package org.apache.ignite.cache.affinity.rackaware;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.processors.cache.GridCacheUtils;
import org.apache.ignite.internal.util.typedef.F;
import org.apache.ignite.internal.util.typedef.internal.LT;
import org.apache.ignite.lang.IgniteBiTuple;
import org.apache.ignite.spi.discovery.zk.ZookeeperDiscoverySpi;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

/**
 * 主机感知
 * Created by xz on 2020/7/22.
 */
public class RendezvousAffinityFunctionTest {
    //private Ignite ignite ;
    private Integer parts ;
    private boolean exclNeighbors;
    /** Comparator. */
    private static final Comparator<IgniteBiTuple<Long, ClusterNode>> COMPARATOR = new RackawareAffinityFunction.HashComparator();
    private boolean exclNeighborsWarn;
    private int nodeSize ;
    @Before
    public void before(){
        //ignite = IgniteUtil.getIgnite() ;
        parts = 2048 ;
        exclNeighbors = true ;
        exclNeighborsWarn = false ;
        nodeSize = 4 ;
    }

    /**
     * 主机感知 neighbors 构造数据  按节点把此节点主机上所有节点捞出来
     */
    @Test
    public void neighbors(){
        Collection<ClusterNode> baselineNodeCollection = IgniteUtil.manyHostOneNode(nodeSize) ;
        Map<UUID, Collection<ClusterNode>> map = GridCacheUtils.neighbors(baselineNodeCollection) ;
        map.forEach((uuid,collection)->{
            System.out.println("------------"+uuid.toString());
            collection.forEach(clusterNode -> {
                System.out.println(clusterNode.id().toString()+"--"+clusterNode.attribute("org.apache.ignite.ips"));
            });
        });

    }
    /**
     * 主机感知 assignPartitions 1.1
     */
    @Test
    public void assignPartitions1_1(){
        Collection<ClusterNode> baselineNodeCollection = IgniteUtil.oneHostManyNode(nodeSize) ;
        List<List<ClusterNode>> assignments = new ArrayList<>(parts);

        //节点  此主机上所有节点
        Map<UUID, Collection<ClusterNode>> neighborhoodCache = GridCacheUtils.neighbors(baselineNodeCollection);

        List<ClusterNode> nodes = new ArrayList<>(baselineNodeCollection);
        /*for (int i = 0; i < 1; i++) {
            List<ClusterNode> partAssignment = assignPartitions1_2(i, nodes, 1, neighborhoodCache);
        }*/
        List<ClusterNode> partAssignment = assignPartitions1_2(2, nodes, 2, neighborhoodCache);

        IgniteUtil.print(partAssignment);
    }

    private List<ClusterNode> assignPartitions1_2(int part,
                                                  List<ClusterNode> nodes,
                                                  int backups,
                                                  @Nullable Map<UUID, Collection<ClusterNode>> neighborhoodCache){
        if (nodes.size() <= 1)
            return nodes;

        IgniteBiTuple<Long, ClusterNode>[] hashArr =
                (IgniteBiTuple<Long, ClusterNode> [])new IgniteBiTuple[nodes.size()];
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

        Collection<ClusterNode> allNeighbors = new HashSet<>();

        ClusterNode primary = it.next();

        res.add(primary);

        if (exclNeighbors){
            //如果主机感知  所有邻居集合中 加入主节点
            allNeighbors.addAll(neighborhoodCache.get(primary.id()));
        }

        // Select backups.
        if (backups > 0) {
            while (it.hasNext() && res.size() < primaryAndBackups) {
                ClusterNode node = it.next();

                if (exclNeighbors) {
                    if (!allNeighbors.contains(node)) {
                        res.add(node);

                        allNeighbors.addAll(neighborhoodCache.get(node.id()));
                    }
                }
            }
        }

        if (res.size() < primaryAndBackups && nodes.size() >= primaryAndBackups && exclNeighbors) {
            // Need to iterate again in case if there are no nodes which pass exclude neighbors backups criteria.
            it = sortedNodes.iterator();

            it.next();

            while (it.hasNext() && res.size() < primaryAndBackups) {
                ClusterNode node = it.next();

                if (!res.contains(node))
                    res.add(node);
            }

            if (!exclNeighborsWarn) {
                System.out.println("Affinity function excludeNeighbors property is ignored " +
                        "because topology has no enough nodes to assign backups.");

                exclNeighborsWarn = true;
            }
        }

        return res;
    }





    @After
    public void after(){
        //IgniteUtil.release(ignite);
    }
}