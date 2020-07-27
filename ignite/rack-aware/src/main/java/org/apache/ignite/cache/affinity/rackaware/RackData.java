package org.apache.ignite.cache.affinity.rackaware;

import org.apache.ignite.cluster.ClusterNode;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xz on 2020/7/27.
 */
public class RackData {
    private  Map<String, Map<String, Collection<ClusterNode>>> rackAllData ;
    private Map<UUID, Collection<ClusterNode>> rackNeighbors ;
    private Map<UUID, Collection<ClusterNode>> rackExcludeMacNeighbors ;

    public RackData(Map<String, Map<String, Collection<ClusterNode>>> rackAllData, Map<UUID, Collection<ClusterNode>> rackNeighbors, Map<UUID, Collection<ClusterNode>> rackExcludeMacNeighbors) {
        this.rackAllData = rackAllData;
        this.rackNeighbors = rackNeighbors;
        this.rackExcludeMacNeighbors = rackExcludeMacNeighbors;
    }

    public Map<String, Map<String, Collection<ClusterNode>>> getRackAllData() {
        return rackAllData;
    }

    public Map<UUID, Collection<ClusterNode>> getRackNeighbors() {
        return rackNeighbors;
    }

    public Map<UUID, Collection<ClusterNode>> getRackExcludeMacNeighbors() {
        return rackExcludeMacNeighbors;
    }
}
