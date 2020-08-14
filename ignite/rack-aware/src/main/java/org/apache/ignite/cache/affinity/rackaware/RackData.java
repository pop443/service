package org.apache.ignite.cache.affinity.rackaware;

import org.apache.ignite.cluster.ClusterNode;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * 机架感知结构体
 * Created by xz on 2020/7/27.
 */
class RackData {
    /**
     * 所有机架的正序结构为
     *    map
     *          rack1
     *                  mac1 (node1 node2 node3)
     *                  mac2 (node1 node2 node3)
     *          rack2
     *                  mac3 (node1 node2 node3)
     *                  mac4 (node1 node2 node3)
     */
    private  Map<String, Map<String, Collection<ClusterNode>>> rackAllData ;
    /**
     *    rackNeighbors 机架感知的倒排索引
     *    map
     *          uuid1   (node1 node2 node3)
     *          uuid2   (node1 node2 node3)
     */
    private Map<UUID, Collection<ClusterNode>> rackNeighbors ;
    /**
     *    rackExcludeMacNeighbors 机架感知排除当前key的ip
     *    map
     *          uuid1   (node1 node2 node3)
     *          uuid2   (node1 node2 node3)
     */
    private Map<UUID, Collection<ClusterNode>> rackExcludeMacNeighbors ;

    RackData(Map<String, Map<String, Collection<ClusterNode>>> rackAllData, Map<UUID, Collection<ClusterNode>> rackNeighbors, Map<UUID, Collection<ClusterNode>> rackExcludeMacNeighbors) {
        this.rackAllData = rackAllData;
        this.rackNeighbors = rackNeighbors;
        this.rackExcludeMacNeighbors = rackExcludeMacNeighbors;
    }

    Map<String, Map<String, Collection<ClusterNode>>> getRackAllData() {
        return rackAllData;
    }

    Map<UUID, Collection<ClusterNode>> getRackNeighbors() {
        return rackNeighbors;
    }

    Map<UUID, Collection<ClusterNode>> getRackExcludeMacNeighbors() {
        return rackExcludeMacNeighbors;
    }
}
