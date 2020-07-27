package org.apache.ignite.cache.affinity.rackaware;

import javafx.util.Pair;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.IgniteNodeAttributes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 机架适配器
 * Created by xz on 2020/7/23.
 */
public class RackAwareAdapt {
    /**
     * 机架信息
     * rackIp,rack
     */
    private static Map<String, String> rackMap = new HashMap<>();
    /**
     * 拓扑的mac和ip映射关系 缓存
     * mac,rackIp
     */
    private static Map<String, String> macIpMapper = new HashMap<>();
    private static String DEFAULT_RACK = "DRACK";

    public RackAwareAdapt(String paths) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(paths);
            properties.load(is);
            Iterator<Map.Entry<Object, Object>> it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Object, Object> entry = it.next();
                rackMap.put((String) entry.getKey(), (String) entry.getValue());
            }
        } catch (Exception e) {
            System.out.println("机架感知配置获取失败：将会使用主机感知 , error" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取机架感知数据结构
     *
     * @param topSnapshot
     * @param simpleRack
     * @return
     */
    public RackData rackNeighbors(Collection<ClusterNode> topSnapshot,boolean simpleRack) {
        if (rackMap.isEmpty()) {
            //代表要使用主机感知
            return null;
        }
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
        Map<String, Map<String, Collection<ClusterNode>>> rackAllData = new HashMap<>(rackMap.size() + 1, 1.0f);
        //如果拓扑中有 机架中没有 则多出来的都认为是默认的一个机架的
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
            if (collection == null) {
                collection = new ArrayList<>();
                map.put(mac, collection);
            }
            collection.add(clusterNode);
        }
        //验证
        if (rackAllData.size()<2){
            //只有一个机架
            return null ;
        }
        if (!simpleRack){
            for (Map<String, Collection<ClusterNode>> map:rackAllData.values()) {
                if (map.size()<2){
                    //只有一个ip
                    return null ;
                }
            }
        }
        /**
         *    rackCollectionMap 所有机架按机架区分的结构为
         *    map
         *          rack1   (node1 node2 node3)
         *          rack2   (node1 node2 node3)
         */
        Map<String, Collection<ClusterNode>> rackCollectionMap = new HashMap<>();
        Iterator<Map.Entry<String, Map<String, Collection<ClusterNode>>>> it = rackAllData.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Map<String, Collection<ClusterNode>>> entry = it.next();
            Map<String, Collection<ClusterNode>> rackMap = entry.getValue();
            Collection<ClusterNode> rackAllCluster = new ArrayList<>();
            Iterator<Collection<ClusterNode>> collectionIterator = rackMap.values().iterator();
            while (collectionIterator.hasNext()) {
                rackAllCluster.addAll(collectionIterator.next());
            }
            rackCollectionMap.put(entry.getKey(), rackAllCluster);
        }
        /**
         *    rackNeighbors 机架感知的倒排索引
         *    map
         *          uuid1   (node1 node2 node3)
         *          uuid2   (node1 node2 node3)
         */
        Map<UUID, Collection<ClusterNode>> rackNeighbors = new HashMap<>(topSnapshot.size());
        /**
         *    macNeighbors 主机感知
         *    map
         *          uuid1   (node1 node2 node3)
         *          uuid2   (node1 node2 node3)
         */
        Map<UUID, Collection<ClusterNode>> rackExcludeMacNeighbors = new HashMap<>(topSnapshot.size());
        for (ClusterNode clusterNode : topSnapshot) {
            String rack = getRack(clusterNode);
            rackNeighbors.put(clusterNode.id(), rackCollectionMap.get(rack));

            if (!simpleRack){
                String mac = getMac(clusterNode) ;
                //循环这个机架下的所有节点
                for (ClusterNode clusterNode1:rackCollectionMap.get(rack)) {
                    String rackExcludeMac = getMac(clusterNode1) ;
                    if (!rackExcludeMac.equals(mac)){
                        Collection<ClusterNode> collection = rackExcludeMacNeighbors.get(clusterNode.id()) ;
                        if (collection==null){
                            collection = new ArrayList<>() ;
                            rackExcludeMacNeighbors.put(clusterNode.id(),collection) ;
                        }
                        collection.add(clusterNode1) ;
                    }
                }
            }
        }
        /**
         *    一个机架下排除当前ip的倒排索引
         *    map
         *          uuid1   (node1 node2 node3)
         *          uuid2   (node1 node2 node3)
         */
        RackData rackData = new RackData(rackAllData,rackNeighbors,rackExcludeMacNeighbors) ;
        return rackData;
    }


    public static String getMac(ClusterNode clusterNode) {
        return clusterNode.attribute(IgniteNodeAttributes.ATTR_MACS);
    }

    public static String getRack(ClusterNode clusterNode){
        String ip = getIp(clusterNode);
        if (ip == null) {
            return DEFAULT_RACK;
        } else {
            return rackMap.get(ip);
        }
    }

    /**
     * 通过mac和ip的映射缓存 获取准确ip
     * 如果缓存中没有 在机架配置中寻找
     * 如果有加入缓存中
     * 如果没有返回null
     *
     * @param clusterNode
     * @return
     */
    private static String getIp(ClusterNode clusterNode) {
        String mac = clusterNode.attribute(IgniteNodeAttributes.ATTR_MACS);
        String retIp = macIpMapper.get(mac);
        if (retIp != null) {
            //mac ip 映射表中存在 直接输出ip地址
            return retIp;
        }

        String clusterNodeIp = clusterNode.attribute(IgniteNodeAttributes.ATTR_IPS);
        Iterator<String> it = rackMap.keySet().iterator();
        while (it.hasNext()) {
            String rackIp = it.next();
            if (clusterNodeIp.contains(rackIp)) {
                macIpMapper.put(mac, rackIp);
                return rackIp;
            }
        }
        return null;
    }

}
