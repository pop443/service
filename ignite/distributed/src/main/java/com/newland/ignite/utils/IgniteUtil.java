package com.newland.ignite.utils;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.TransactionConfiguration;
import org.apache.ignite.events.EventType;
import org.apache.ignite.internal.util.IgniteUtils;
import org.apache.ignite.logger.log4j2.Log4J2Logger;
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
        //spi.setZkConnectionString("10.33.254.188:30230,10.33.254.189:30230,10.33.254.190:30230");
        //spi.setZkConnectionString("10.32.48.26:2902,10.32.48.27:2902,10.32.48.28:2902");
        spi.setZkConnectionString("172.32.148.244:2181,172.32.148.245:2181,172.32.148.246:2181");

        //spi.setZkConnectionString("127.0.0.1:2181");

        spi.setSessionTimeout(60000);
        spi.setZkRootPath("/xzIgnite280") ;
        spi.setJoinTimeout(30000);
        cfg.setDiscoverySpi(spi);
        cfg.setClientMode(true);
        cfg.setDeploymentMode(DeploymentMode.SHARED);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setSystemWorkerBlockedTimeout(120000);
        /*System.out.println(IgniteUtils.getIgniteHome());
       try {
            Log4J2Logger log4J2Logger = new Log4J2Logger(logger,"log4j2.xml");
            cfg.setGridLogger(log4J2Logger) ;
        } catch (IgniteCheckedException e) {
            System.out.println("------------------\r\n"+e.getMessage()+"------------------\r\n");
        }*/


        TransactionConfiguration transactionConfiguration = new TransactionConfiguration() ;
        transactionConfiguration.setTxTimeoutOnPartitionMapExchange(20000L);
        cfg.setTransactionConfiguration(transactionConfiguration) ;

        cfg.setIncludeEventTypes(EventType.EVT_CACHE_REBALANCE_PART_DATA_LOST
                ,EventType.EVT_CACHE_REBALANCE_STARTED
                ,EventType.EVT_CACHE_REBALANCE_STOPPED);

        return cfg ;
    }

    public static Ignite getIgnite(){
        System.setProperty("IGNITE_QUIET","false") ;
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
