package org.apache.ignite.cache.affinity.rackaware;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.affinity.AffinityFunction;
import org.apache.ignite.cache.affinity.AffinityFunctionContext;
import org.apache.ignite.cache.affinity.rendezvous.RendezvousAffinityFunction;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.internal.processors.cache.GridCacheUtils;
import org.apache.ignite.internal.util.typedef.F;
import org.apache.ignite.internal.util.typedef.internal.A;
import org.apache.ignite.internal.util.typedef.internal.LT;
import org.apache.ignite.internal.util.typedef.internal.U;
import org.apache.ignite.lang.IgniteBiTuple;
import org.apache.ignite.resources.LoggerResource;
import org.apache.ignite.resources.SpringResource;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

/**
 * 数据机架感知
 * Created by xz on 2020/7/22.
 */
public class RackawareAffinityFunction implements AffinityFunction, Serializable {
    private int parts;
    private int mask = -1;
    /**
     * 仅一次告警
     */
    private transient boolean exclRackWarn;
    private transient boolean simpleRack;
    private static final Comparator<IgniteBiTuple<Long, ClusterNode>> COMPARATOR = new HashComparator();

    @LoggerResource
    private transient IgniteLogger log;

    @SpringResource(resourceName = "rackAwareAdapt")
    private transient RackAwareAdapt rackAwareAdapt;

    public RackawareAffinityFunction(int parts) {
        this(parts, true);
    }

    public RackawareAffinityFunction(int parts, boolean simpleRack) {
        this.parts = parts;
        this.exclRackWarn = false;
        setPartitions(parts);
        this.simpleRack = simpleRack;
    }

    private void setPartitions(int parts) {
        A.ensure(parts <= CacheConfiguration.MAX_PARTITIONS_COUNT,
                "parts <= " + CacheConfiguration.MAX_PARTITIONS_COUNT);
        A.ensure(parts > 0, "parts > 0");
        this.parts = parts;
        mask = calculateMask(parts);
    }


    @Override
    public List<List<ClusterNode>> assignPartitions(AffinityFunctionContext affCtx) {
        List<List<ClusterNode>> assignments = new ArrayList<>(parts);

        List<ClusterNode> nodes = affCtx.currentTopologySnapshot();
        RackData rackData = rackAwareAdapt.rackNeighbors(nodes, simpleRack);

        //如果机架信息为null 备份数据为1 走主机感知
        if (rackData == null || affCtx.backups() < 2) {
            Map<UUID, Collection<ClusterNode>> neighborhoodCache = GridCacheUtils.neighbors(affCtx.currentTopologySnapshot());
            for (int i = 0; i < parts; i++) {
                List<ClusterNode> partAssignment = assignPartition(i, nodes, affCtx.backups(), neighborhoodCache);
                assignments.add(partAssignment);
            }
            return assignments;
        }
        for (int i = 0; i < parts; i++) {
            List<ClusterNode> partAssignment = assignPartition(i, nodes, affCtx.backups(), rackData);
            assignments.add(partAssignment);
        }
        return assignments;
    }

    /**
     * 机架感知分配
     *
     * @param part
     * @param nodes
     * @param backups
     * @param rackData
     * @return
     */
    private List<ClusterNode> assignPartition(int part,
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

            rackAwareWarn("Affinity function rackAware property is ignored because topology has no enough nodes to assign backups");
        }


        return res;
    }

    public List<ClusterNode> assignPartition(int part,
                                             List<ClusterNode> nodes,
                                             int backups,
                                             @Nullable Map<UUID, Collection<ClusterNode>> neighborhoodCache) {
        if (nodes.size() <= 1)
            return nodes;

        IgniteBiTuple<Long, ClusterNode>[] hashArr =
                (IgniteBiTuple<Long, ClusterNode>[]) new IgniteBiTuple[nodes.size()];

        for (int i = 0; i < nodes.size(); i++) {
            ClusterNode node = nodes.get(i);

            Object nodeHash = resolveNodeHash(node);

            long hash = hash(nodeHash.hashCode(), part);

            hashArr[i] = F.t(hash, node);
        }

        final int primaryAndBackups = backups == Integer.MAX_VALUE ? nodes.size() : Math.min(backups + 1, nodes.size());

        Iterable<ClusterNode> sortedNodes = new LazyLinearSortedContainer(hashArr, primaryAndBackups);

        // REPLICATED cache case
        if (backups == Integer.MAX_VALUE)
            return replicatedAssign(nodes, sortedNodes);

        Iterator<ClusterNode> it = sortedNodes.iterator();

        List<ClusterNode> res = new ArrayList<>(primaryAndBackups);

        Collection<ClusterNode> allNeighbors = new HashSet<>();

        ClusterNode primary = it.next();

        res.add(primary);

        allNeighbors.addAll(neighborhoodCache.get(primary.id()));

        // Select backups.
        if (backups > 0) {
            while (it.hasNext() && res.size() < primaryAndBackups) {
                ClusterNode node = it.next();

                if (!allNeighbors.contains(node)) {
                    res.add(node);

                    allNeighbors.addAll(neighborhoodCache.get(node.id()));
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

            rackAwareWarn("Affinity function macAware property is ignored because topology has no enough nodes to assign backups");
        }

        assert res.size() <= primaryAndBackups;

        return res;
    }


    @Override
    public void reset() {
        // No-op.
    }

    @Override
    public int partitions() {
        return parts;
    }

    @Override
    public int partition(Object key) {
        if (key == null)
            throw new IllegalArgumentException("Null key is passed for a partition calculation. " +
                    "Make sure that an affinity key that is used is initialized properly.");

        return calculatePartition(key, mask, parts);
    }

    @Override
    public void removeNode(UUID nodeId) {
        // No-op.
    }

    private void rackAwareWarn(String msg) {
        if (!exclRackWarn) {
            LT.warn(log, msg);

            exclRackWarn = true;
        }
    }

    static Object resolveNodeHash(ClusterNode node) {
        return node.consistentId();
    }

    static long hash(int key0, int key1) {
        long key = (key0 & 0xFFFFFFFFL)
                | ((key1 & 0xFFFFFFFFL) << 32);

        key = (~key) + (key << 21); // key = (key << 21) - key - 1;
        key ^= (key >>> 24);
        key += (key << 3) + (key << 8); // key * 265
        key ^= (key >>> 14);
        key += (key << 2) + (key << 4); // key * 21
        key ^= (key >>> 28);
        key += (key << 31);

        return key;
    }

    static class LazyLinearSortedContainer implements Iterable<ClusterNode> {
        /**
         * Initial node-hash array.
         */
        private final IgniteBiTuple<Long, ClusterNode>[] arr;

        /**
         * Count of the sorted elements
         */
        private int sorted;

        /**
         * @param arr                Node / partition hash list.
         * @param needFirstSortedCnt Estimate count of elements to return by iterator.
         */
        LazyLinearSortedContainer(IgniteBiTuple<Long, ClusterNode>[] arr, int needFirstSortedCnt) {
            this.arr = arr;

            if (needFirstSortedCnt > (int) Math.log(arr.length)) {
                Arrays.sort(arr, COMPARATOR);

                sorted = arr.length;
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<ClusterNode> iterator() {
            return new LazyLinearSortedContainer.SortIterator();
        }

        /**
         *
         */
        private class SortIterator implements Iterator<ClusterNode> {
            /**
             * Index of the first unsorted element.
             */
            private int cur;

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return cur < arr.length;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public ClusterNode next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                if (cur < sorted)
                    return arr[cur++].get2();

                IgniteBiTuple<Long, ClusterNode> min = arr[cur];

                int minIdx = cur;

                for (int i = cur + 1; i < arr.length; i++) {
                    if (COMPARATOR.compare(arr[i], min) < 0) {
                        minIdx = i;

                        min = arr[i];
                    }
                }

                if (minIdx != cur) {
                    arr[minIdx] = arr[cur];

                    arr[cur] = min;
                }

                sorted = cur++;

                return min.get2();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove doesn't supported");
            }
        }
    }

    static List<ClusterNode> replicatedAssign(List<ClusterNode> nodes, Iterable<ClusterNode> sortedNodes) {
        ClusterNode primary = sortedNodes.iterator().next();

        List<ClusterNode> res = new ArrayList<>(nodes.size());

        res.add(primary);

        for (ClusterNode n : nodes)
            if (!n.equals(primary))
                res.add(n);

        assert res.size() == nodes.size() : "Not enough backups: " + res.size();

        return res;
    }

    static class HashComparator implements Comparator<IgniteBiTuple<Long, ClusterNode>>, Serializable {
        /** */
        private static final long serialVersionUID = 0L;

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(IgniteBiTuple<Long, ClusterNode> o1, IgniteBiTuple<Long, ClusterNode> o2) {
            return o1.get1() < o2.get1() ? -1 : o1.get1() > o2.get1() ? 1 :
                    o1.get2().id().compareTo(o2.get2().id());
        }
    }

    public static int calculatePartition(Object key, int mask, int parts) {
        if (mask >= 0) {
            int h;

            return ((h = key.hashCode()) ^ (h >>> 16)) & mask;
        }

        return U.safeAbs(key.hashCode() % parts);
    }

    public static int calculateMask(int parts) {
        return (parts & (parts - 1)) == 0 ? parts - 1 : -1;
    }
}
