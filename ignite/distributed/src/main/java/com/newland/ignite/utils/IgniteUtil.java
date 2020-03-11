package com.newland.ignite.utils;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.TransactionConfiguration;
import org.apache.ignite.events.EventType;
import org.apache.ignite.spi.discovery.zk.ZookeeperDiscoverySpi;

/**
 * Created by Administrator on 2019/12/25.
 */
public class IgniteUtil {
    private static Ignite ignite ;

    static {
        IgniteConfiguration cfg = getIgniteConfiguration() ;
        ignite = Ignition.start(cfg);
    }

    public static IgniteConfiguration getIgniteConfiguration(){
        IgniteConfiguration cfg = new IgniteConfiguration();

        ZookeeperDiscoverySpi spi = new ZookeeperDiscoverySpi();
        spi.setZkConnectionString("172.32.148.244:2181,172.32.148.245:2181,172.32.148.246:2181");
        spi.setSessionTimeout(60000);
        spi.setZkRootPath("/xzIgnite280") ;
        spi.setJoinTimeout(30000);
        cfg.setDiscoverySpi(spi);
        cfg.setClientMode(true);
        cfg.setDeploymentMode(DeploymentMode.SHARED);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setSystemWorkerBlockedTimeout(120000);

        TransactionConfiguration transactionConfiguration = new TransactionConfiguration() ;
        transactionConfiguration.setTxTimeoutOnPartitionMapExchange(20000L);
        cfg.setTransactionConfiguration(transactionConfiguration) ;

        cfg.setIncludeEventTypes(EventType.EVT_CACHE_REBALANCE_PART_DATA_LOST);
        return cfg ;
    }

    public static Ignite getIgnite(){
        return ignite ;
    }

    public static Ignite getIgniteByXml(){
        return Ignition.start("node-config.xml") ;
    }

    public static Ignite getIgniteByXml(String path){
        return Ignition.start(path) ;
    }

    public static void release(Ignite ignite){
        if (ignite!=null){
            ignite.close();
        }
    }

    public static <T> BinaryObject toBinary(T t){
        return Ignition.ignite().binary().toBinary(t) ;
    }
}
