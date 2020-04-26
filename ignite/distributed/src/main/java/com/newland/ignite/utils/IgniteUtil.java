package com.newland.ignite.utils;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.TransactionConfiguration;
import org.apache.ignite.events.EventType;
import org.apache.ignite.spi.discovery.zk.ZookeeperDiscoverySpi;
import org.apache.ignite.ssl.SslContextFactory;

/**
 * Created by Administrator on 2019/12/25.
 */
public class IgniteUtil {
    private static Ignite ignite ;

    public static IgniteConfiguration getIgniteConfiguration(){
        IgniteConfiguration cfg = new IgniteConfiguration();

        ZookeeperDiscoverySpi spi = new ZookeeperDiscoverySpi();
        spi.setZkConnectionString("172.32.148.244:2181,172.32.148.245:2181,172.32.148.246:2181");
        //spi.setZkConnectionString("127.0.0.1:2181");

        spi.setSessionTimeout(60000);
        spi.setZkRootPath("/xzIgniteBoss") ;
        spi.setJoinTimeout(30000);
        cfg.setDiscoverySpi(spi);
        cfg.setClientMode(true);
        cfg.setDeploymentMode(DeploymentMode.SHARED);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setSystemWorkerBlockedTimeout(120000);


        TransactionConfiguration transactionConfiguration = new TransactionConfiguration() ;
        transactionConfiguration.setTxTimeoutOnPartitionMapExchange(20000L);
        cfg.setTransactionConfiguration(transactionConfiguration) ;

        cfg.setIncludeEventTypes(EventType.EVT_CACHE_REBALANCE_PART_DATA_LOST
                ,EventType.EVT_CACHE_REBALANCE_STARTED
                ,EventType.EVT_CACHE_REBALANCE_STOPPED);

        return cfg ;
    }

    public static Ignite getIgnite(){
        IgniteConfiguration cfg = getIgniteConfiguration() ;
        ignite = Ignition.start(cfg);
        return ignite ;
    }

    public static Ignite getIgniteSecurity(){
        IgniteConfiguration cfg = getIgniteConfiguration() ;
        SslContextFactory factory = new SslContextFactory();

        factory.setKeyStoreFilePath("ignite_client_keystore.jks");
        factory.setKeyStorePassword("123456".toCharArray());
        factory.setTrustManagers(SslContextFactory.getDisabledTrustManager());
        factory.setProtocol("SSL");

        cfg.setSslContextFactory(factory);
        ignite = Ignition.start(cfg);
        return ignite ;
    }

    public static Ignite getIgniteByXml(){
        return Ignition.start("node-config-manyDS.xml") ;
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
