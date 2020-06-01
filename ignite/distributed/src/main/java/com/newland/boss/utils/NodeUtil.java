package com.newland.boss.utils;

import javafx.util.Pair;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.cluster.BaselineNode;
import org.apache.ignite.cluster.ClusterNode;

import java.util.*;

/**
 * Created by xz on 2020/6/1.
 */
public class NodeUtil {
    public static Collection<BaselineNode> getBaseline(IgniteCluster igniteCluster){
        return igniteCluster.currentBaselineTopology() ;
    }

    public static int getBaselineIndex(Collection<BaselineNode> collection, ClusterNode clusterNode) {
        List<String> list = new ArrayList<>() ;
        for (BaselineNode baselineNode:collection) {
            list.add(baselineNode.consistentId().toString()) ;
        }
        Collections.sort(list);
        Map<String,Integer> map = new HashMap<>() ;
        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i),i+1) ;
        }
        return map.get(clusterNode.consistentId().toString()) ;
    }

    public static Pair<Long,Long>[] getPageInfo(Long dataCount,int nodeIndex,int nodeCount,int parallel){
        Pair[] pairs = new Pair[parallel] ;
        Long simpleDataCount = dataCount/nodeCount ;
        Long simpleDataCountFrom = simpleDataCount*(nodeIndex-1) ;
        for (int i = 0; i < parallel; i++) {
            Long simpleDataCountParallel = simpleDataCount/parallel ;
            Long simpleDataCountFromParallel = simpleDataCountFrom+simpleDataCountParallel*(i);
            Long simpleDataCountToParallel = simpleDataCountFrom+simpleDataCountParallel*(i+1);
            Pair<Long,Long> pair = new Pair<>(simpleDataCountFromParallel,simpleDataCountToParallel) ;
            pairs[i] = pair ;
        }

        return pairs ;
    }

    public static void main(String[] args) {
        Long dataCount = 401L ;
        int nodeIndex = 1 ;
        int nodeCount = 3 ;
        int parallel = 4 ;
        Pair<Long,Long>[] pairs = NodeUtil.getPageInfo(dataCount,nodeIndex,nodeCount,parallel) ;
        for (Pair<Long,Long> pair:pairs) {
            System.out.println("from "+pair.getKey()+" to "+pair.getValue());
        }
    }
}
