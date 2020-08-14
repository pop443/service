package org.apache.ignite.cache.affinity.rackaware;

import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.IgniteNodeAttributes;

import java.util.*;

/**
 * 机架适配器
 * Created by xz on 2020/7/23.
 */
public class RackAwareAdapt {

    public static final String ATTR_RACK = IgniteNodeAttributes.ATTR_PREFIX + ".rack";

    public static String DEFAULT_RACK = "DRACK";


    /**
     * 获取机架感知数据结构
     *
     * @param topSnapshot
     * @param simpleRack
     * @return
     */
    public static RackData rackNeighbors(Collection<ClusterNode> topSnapshot, boolean simpleRack) {
        Map<String, Map<String, Collection<ClusterNode>>> rackAllData = new HashMap<>(topSnapshot.size(), 1.0f);
        //如果属性中没有rack信息 则归属默认机架
        for (ClusterNode clusterNode : topSnapshot) {
            //当ip为null 时   代表拓扑有 机架没有
            String mac = getMac(clusterNode);
            String rack = getRack(clusterNode);
            Map<String, Collection<ClusterNode>> map = rackAllData.get(rack);
            if (map == null) {
                map = new HashMap<>();
                rackAllData.put(rack, map);
            }
            Collection<ClusterNode> collection = map.get(mac);
            //冗余写法  编译有问题 不可修改
            if (collection == null) {
                collection = new ArrayList<>();
                map.put(mac, collection);
                collection.add(clusterNode);
            }else{
                collection.add(clusterNode);
            }

        }
        //验证
        if (rackAllData.size() < 2) {
            //只有一个机架
            return null;
        }

        Map<UUID, Collection<ClusterNode>> rackNeighbors = new HashMap<>(topSnapshot.size());

        Map<UUID, Collection<ClusterNode>> rackExcludeMacNeighbors = new HashMap<>(topSnapshot.size());
        for (ClusterNode clusterNode : topSnapshot) {
            String rack = getRack(clusterNode);
            String mac = getMac(clusterNode);
            //循环这个机架下的所有节点
            Map<String, Collection<ClusterNode>> rackMap = rackAllData.get(rack);
            Iterator<Map.Entry<String, Collection<ClusterNode>>> rackMapIt = rackMap.entrySet().iterator();
            while (rackMapIt.hasNext()) {
                Map.Entry<String, Collection<ClusterNode>> rackMapItEntry = rackMapIt.next();
                Collection<ClusterNode> rackCollection = rackNeighbors.get(clusterNode.id());
                if (rackCollection == null) {
                    rackCollection = new ArrayList<>();
                    rackNeighbors.put(clusterNode.id(), rackCollection);
                }
                rackCollection.addAll(rackMapItEntry.getValue());
                if (simpleRack) {
                    continue;
                }
                if (!mac.equals(rackMapItEntry.getKey())) {
                    Collection<ClusterNode> collection = rackExcludeMacNeighbors.get(clusterNode.id());
                    if (collection == null) {
                        collection = new ArrayList<>();
                        rackExcludeMacNeighbors.put(clusterNode.id(), collection);
                    }
                    collection.addAll(rackMapItEntry.getValue());
                }
            }
        }

        /**
         * 一个机架下排除当前ip的倒排索引
         * map
         * uuid1   (node1 node2 node3)
         * uuid2   (node1 node2 node3)
         */
        RackData rackData = new RackData(rackAllData, rackNeighbors, rackExcludeMacNeighbors);
        return rackData;
    }


    public static String getMac(ClusterNode clusterNode) {
        return clusterNode.attribute(IgniteNodeAttributes.ATTR_MACS);
    }

    /**
     * 如果没有机架信息 则为默认机架
     *
     * @param clusterNode
     * @return
     */
    public static String getRack(ClusterNode clusterNode) {
        return clusterNode.attribute(ATTR_RACK) == null ? DEFAULT_RACK : clusterNode.attribute(ATTR_RACK);
    }

}
