package org.apache.ignite.cache.affinity.rackaware;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.IgniteNodeAttributes;
import org.apache.ignite.lang.IgniteProductVersion;
import org.apache.ignite.spi.discovery.zk.ZookeeperDiscoverySpi;
import org.apache.ignite.spi.discovery.zk.internal.ZookeeperClusterNode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by xz on 2020/7/22.
 */
public class IgniteUtil {
    public static Ignite getIgnite() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        ZookeeperDiscoverySpi spi = new ZookeeperDiscoverySpi();
        spi.setZkConnectionString("172.32.148.244:2181,172.32.148.245:2181,172.32.148.246:2181");
        spi.setSessionTimeout(60000);
        spi.setZkRootPath("/xzIgniteBoss");
        spi.setJoinTimeout(30000);
        cfg.setDiscoverySpi(spi);
        cfg.setClientMode(true);
        cfg.setDeploymentMode(DeploymentMode.SHARED);
        cfg.setPeerClassLoadingEnabled(true);
        return Ignition.start(cfg);
    }

    public static void release(Ignite ignite) {
        if (ignite != null) {
            ignite.close();
        }
    }

    public static List<ClusterNode> oneHostOneNode(int nodeSize) {
        Map<String, String> map = getRackInfo();
        List<ClusterNode> clusterNodes = new ArrayList<>();
        int index = 110;
        UUID uuid = UUID.fromString("1-2-3-4-" + index);
        Collection<String> addrs = new HashSet<>();
        addrs.add("172.32.148.110");

        Collection<String> hostNames = new HashSet<>();
        hostNames.add("host" + index);

        IgniteProductVersion productVersion = null;

        Map<String, Object> attrs = new HashMap<>();
        attrs.put("xz.info", index);
        attrs.put(IgniteNodeAttributes.ATTR_IPS, "172.32.148.10");
        attrs.put(IgniteNodeAttributes.ATTR_MACS, "mac" + attrs.get(IgniteNodeAttributes.ATTR_IPS));
        String rack = map.get(attrs.get(IgniteNodeAttributes.ATTR_IPS));
        attrs.put(RackAwareAdapt.ATTR_RACK, rack == null ? RackAwareAdapt.DEFAULT_RACK : rack);

        String consistentId = "consistentId" + index;
        ClusterNode clusterNode = new ZookeeperClusterNode(uuid, addrs, hostNames, productVersion, attrs, consistentId, 1000, false, null);
        clusterNodes.add(clusterNode);
        return clusterNodes;
    }


    public static List<ClusterNode> oneHostManyNode(int nodeSize) {
        Map<String, String> map = getRackInfo();
        List<ClusterNode> clusterNodes = new ArrayList<>();
        for (int i = 0; i < nodeSize; i++) {
            int index = 110 + i;
            UUID uuid = UUID.fromString("1-2-3-4-" + index);
            Collection<String> addrs = new HashSet<>();
            addrs.add("172.32.148.110");

            Collection<String> hostNames = new HashSet<>();
            hostNames.add("host" + index);

            IgniteProductVersion productVersion = null;

            Map<String, Object> attrs = new HashMap<>();
            attrs.put("xz.info", index);
            attrs.put(IgniteNodeAttributes.ATTR_IPS, "172.32.148.10");
            attrs.put(IgniteNodeAttributes.ATTR_MACS, "mac" + attrs.get(IgniteNodeAttributes.ATTR_IPS));
            String rack = map.get(attrs.get(IgniteNodeAttributes.ATTR_IPS));
            attrs.put(RackAwareAdapt.ATTR_RACK, rack == null ? RackAwareAdapt.DEFAULT_RACK : rack);

            String consistentId = "consistentId" + index;
            ClusterNode clusterNode = new ZookeeperClusterNode(uuid, addrs, hostNames, productVersion, attrs, consistentId, 1000, false, null);
            clusterNodes.add(clusterNode);
        }
        return clusterNodes;
    }

    public static List<ClusterNode> manyHostOneNode(int nodeSize) {
        Map<String, String> map = getRackInfo();
        List<ClusterNode> clusterNodes = new ArrayList<>();

        for (int i = 0; i < nodeSize; i++) {
            int index = 110 + i;
            UUID uuid = UUID.fromString("1-2-3-4-" + index);
            Collection<String> addrs = new HashSet<>();
            addrs.add("172.32.148." + index);

            Collection<String> hostNames = new HashSet<>();
            hostNames.add("host" + index);

            IgniteProductVersion productVersion = null;

            Map<String, Object> attrs = new HashMap<>();
            attrs.put("xz.info", index);
            attrs.put(IgniteNodeAttributes.ATTR_IPS, "172.32.148." + index);
            attrs.put(IgniteNodeAttributes.ATTR_MACS, "mac" + attrs.get(IgniteNodeAttributes.ATTR_IPS));
            String rack = map.get(attrs.get(IgniteNodeAttributes.ATTR_IPS));
            attrs.put(RackAwareAdapt.ATTR_RACK, rack == null ? RackAwareAdapt.DEFAULT_RACK : rack);

            String consistentId = "consistentId" + index;
            ClusterNode clusterNode = new ZookeeperClusterNode(uuid, addrs, hostNames, productVersion, attrs, consistentId, 1000, false, null);
            clusterNodes.add(clusterNode);
        }
        return clusterNodes;
    }

    public static List<ClusterNode> manyHostManyNode(int nodeSize) {
        Map<String, String> map = getRackInfo();
        List<ClusterNode> clusterNodes = new ArrayList<>();
        for (int i = 0; i < nodeSize; i++) {
            int index = 110 + i;
            String ip = "172.32.148." + index;
            for (int j = 0; j < 4; j++) {
                UUID uuid = UUID.fromString("1-2-3-4-" + index + j);
                Collection<String> addrs = new HashSet<>();
                addrs.add(ip);

                Collection<String> hostNames = new HashSet<>();
                hostNames.add("host" + index);

                IgniteProductVersion productVersion = null;

                Map<String, Object> attrs = new HashMap<>();
                attrs.put("xz.info", index + "-" + j);
                attrs.put(IgniteNodeAttributes.ATTR_IPS, ip);
                attrs.put(IgniteNodeAttributes.ATTR_MACS, "mac" + attrs.get(IgniteNodeAttributes.ATTR_IPS));
                String rack = map.get(attrs.get(IgniteNodeAttributes.ATTR_IPS));
                attrs.put(RackAwareAdapt.ATTR_RACK, rack == null ? RackAwareAdapt.DEFAULT_RACK : rack);

                String consistentId = "consistentId" + index + "-" + j;
                ClusterNode clusterNode = new ZookeeperClusterNode(uuid, addrs, hostNames, productVersion, attrs, consistentId, 1000, false, null);
                clusterNodes.add(clusterNode);
            }
        }
        return clusterNodes;
    }

    public static void print(List<ClusterNode> partAssignment) {
        for (ClusterNode clusterNode : partAssignment) {
            System.out.println(clusterNode.addresses().iterator().next() + "--" + clusterNode.attribute("xz.info"));
        }
    }


    private static Map<String, String> getRackInfo() {
        Map<String, String> map = new HashMap<>();
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = IgniteUtil.class.getClassLoader().getResourceAsStream("rack.properties");
            properties.load(is);
            Iterator<Map.Entry<Object, Object>> it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Object, Object> entry = it.next();
                String ip = (String) entry.getKey();
                String rack = (String) entry.getValue();
                map.put(ip, rack);
            }
        } catch (Exception e) {
            System.out.println("-- rack aware error:" + e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
