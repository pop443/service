package org.apache.ignite.cache.affinity.rackaware;

import javafx.util.Pair;
import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.processors.cache.GridCacheUtils;
import org.apache.ignite.internal.util.typedef.F;
import org.apache.ignite.internal.util.typedef.internal.LT;
import org.apache.ignite.lang.IgniteBiTuple;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xz on 2020/7/22.
 */
public class RackawareAffinityFunctionTest {
    //private Ignite ignite ;
    private int nodeSize;
    private RackAwareAdapt rackAwareAdapt;
    private Integer parts;
    private boolean exclRackWarn;
    private Integer backup;
    private boolean simpleRack;

    @Before
    public void before() {
        backup = 2;
        //ignite = IgniteUtil.getIgnite() ;
        nodeSize = 10;
        rackAwareAdapt = new RackAwareAdapt(RackawareAffinityFunctionTest.class.getClassLoader().getResource("rack.properties").getPath());
        parts = 2048;
        exclRackWarn = false;
        simpleRack = false;
    }

    @Test
    public void neighbors() {
        Collection<ClusterNode> baselineNodeCollection = IgniteUtil.manyHostManyNode(nodeSize);
        RackData rackData = rackAwareAdapt.rackNeighbors(baselineNodeCollection, simpleRack);
        if (rackData == null || backup < 2) {
            System.out.println("走主机感知！");
            return;
        }
        //验证数量
        Map<String, Map<String, Collection<ClusterNode>>> rackNeighborhoodCache = rackData.getRackAllData();
        StringBuilder sb1 = new StringBuilder();
        AtomicInteger integer = new AtomicInteger(0);
        rackNeighborhoodCache.forEach((rack, rackNeighbors) -> {
            sb1.append(rack).append("\r\n");
            rackNeighbors.forEach((ip, collection) -> {
                sb1.append("\t").append(ip).append("\r\n");
                collection.forEach(clusterNode -> {
                    integer.addAndGet(1);
                    sb1.append("\t\t").append(clusterNode.id().toString()).append("\r\n");
                });
            });
        });
        System.out.println(sb1.toString());
        Map<UUID, Collection<ClusterNode>> rackNeighbors = rackData.getRackNeighbors();
        printMap(baselineNodeCollection, rackNeighbors);
        System.out.println("------------------------------------------");
        Map<UUID, Collection<ClusterNode>> rackExcludeIpNeighbors = rackData.getRackNeighbors();
        printMap(baselineNodeCollection, rackExcludeIpNeighbors);
    }

    private void printMap(Collection<ClusterNode> baselineNodeCollection, Map<UUID, Collection<ClusterNode>> invertedMap) {
        StringBuilder sb2 = new StringBuilder();
        for (ClusterNode clusterNode : baselineNodeCollection) {
            sb2.append(clusterNode.id().toString()).append("\r\n");
            invertedMap.get(clusterNode.id()).forEach(clusterNode1 -> {
                sb2.append("\t").append(clusterNode1.id()).append("\r\n");
            });
        }
        System.out.println(sb2.toString());
    }

    @Test
    public void assignPartitions1_1() {
        Collection<ClusterNode> baselineNodeCollection = IgniteUtil.oneHostManyNode(nodeSize);

        //节点  此主机上所有节点
        RackData rackData = rackAwareAdapt.rackNeighbors(baselineNodeCollection, simpleRack);
        if (rackData == null || backup < 2) {
            System.out.println("走主机感知");
            return;
        }
        List<ClusterNode> nodes = new ArrayList<>(baselineNodeCollection);

        for (int i = 0; i < 20; i++) {
            List<ClusterNode> partAssignment = assignPartitions1_2(i, nodes, backup, rackData);
            for (ClusterNode clusterNode : partAssignment) {
                System.out.print(RackAwareAdapt.getRack(clusterNode) + "-" + RackAwareAdapt.getMac(clusterNode) + "\t\t\t");
            }
            System.out.println();
        }
    }

    /**
     * 机架感知 第二份数据如果在同机架不同ip时  会存在性能问题   需要多次循环
     *
     * @param part
     * @param nodes
     * @param backups
     * @param rackData
     * @return
     */
    private List<ClusterNode> assignPartitions1_2(int part,
                                                  List<ClusterNode> nodes,
                                                  int backups,
                                                  RackData rackData) {
        if (nodes.size() <= 1)
            return nodes;

        IgniteBiTuple<Long, ClusterNode>[] hashArr =
                (IgniteBiTuple<Long, ClusterNode>[]) new IgniteBiTuple[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            ClusterNode node = nodes.get(i);
            //consistentId
            Object nodeHash = RackawareAffinityFunction.resolveNodeHash(node);
            //consistentId 跟分区数的hash
            long hash = RackawareAffinityFunction.hash(nodeHash.hashCode(), part);

            hashArr[i] = F.t(hash, node);
        }
        //确定是全复制 还是分区 全复制的backups为Integer.MAX_VALUE   分区为 节点数与副本数的小值
        final int primaryAndBackups = backups == Integer.MAX_VALUE ? nodes.size() : Math.min(backups + 1, nodes.size());

        Iterable<ClusterNode> sortedNodes = new RackawareAffinityFunction.LazyLinearSortedContainer(hashArr, primaryAndBackups);
        if (backups == Integer.MAX_VALUE)
            return RackawareAffinityFunction.replicatedAssign(nodes, sortedNodes);


        Iterator<ClusterNode> it = sortedNodes.iterator();

        List<ClusterNode> res = new ArrayList<>(primaryAndBackups);
        ClusterNode primary = it.next();
        res.add(primary);

        Map<UUID, Collection<ClusterNode>> rackNeighborsMap = rackData.getRackNeighbors();
        Map<UUID, Collection<ClusterNode>> rackExcludeMacNeighbors = rackData.getRackExcludeMacNeighbors();
        Collection<ClusterNode> rackNeighbors = new HashSet<>();
        rackNeighbors.addAll(rackNeighborsMap.get(primary.id()));
        if (backups > 0) {
            while (it.hasNext() && res.size() < primaryAndBackups) {
                ClusterNode node = it.next();
                if (simpleRack) {
                    if (!rackNeighbors.contains(node)) {
                        //如果排除的不包含这个节点
                        res.add(node);
                        rackNeighbors.addAll(rackNeighborsMap.get(node.id()));
                    }
                    continue;
                }
                if (res.size() % 2 == 1) {
                    //第2 4 6个备份数据 换一个机架放数据
                    if (!rackNeighbors.contains(node)) {
                        //如果排除的不包含这个节点
                        res.add(node);
                        rackNeighbors.addAll(rackNeighborsMap.get(node.id()));
                    }
                }
                if (res.size() % 2 == 0) {
                    //第1 3 5个备份数据 换上一个机架上的不同ip的节点
                    ClusterNode lastClusterNode = res.get(res.size() - 1);
                    Collection<ClusterNode> collection = rackExcludeMacNeighbors.get(lastClusterNode.id());
                    if (collection == null) {
                        continue;
                    }
                    for (ClusterNode clusterNode : collection) {
                        if (!RackAwareAdapt.getMac(lastClusterNode).equals(RackAwareAdapt.getMac(clusterNode))) {
                            //如果排除的不包含这个节点
                            res.add(clusterNode);
                            break;
                        }
                    }
                }
            }
        }

        if (res.size() < primaryAndBackups && nodes.size() >= primaryAndBackups) {
            // Need to iterate again in case if there are no nodes which pass exclude neighbors backups criteria.
            it = sortedNodes.iterator();

            it.next();

            while (it.hasNext() && res.size() < primaryAndBackups) {
                ClusterNode node = it.next();

                if (!res.contains(node))
                    res.add(node);
            }

            System.out.println(part + "part,Affinity function excludeRackNeighbors property is ignored " +
                    "because topology has no enough nodes to assign backups");
        }


        return res;
    }


    @After
    public void after() {
        //IgniteUtil.release(ignite);
    }
}