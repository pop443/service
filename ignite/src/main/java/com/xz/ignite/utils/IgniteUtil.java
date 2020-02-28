package com.xz.ignite.utils;

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

    public static IgniteConfiguration getIgniteConfiguration(){
        IgniteConfiguration cfg = new IgniteConfiguration();

        ZookeeperDiscoverySpi spi = new ZookeeperDiscoverySpi();
        spi.setZkConnectionString("172.32.148.244:2181,172.32.148.245:2181,172.32.148.246:2181");
        spi.setSessionTimeout(60000);
        spi.setZkRootPath("/xzIgnite") ;
        spi.setJoinTimeout(30000);
        cfg.setDiscoverySpi(spi);
        cfg.setClientMode(true);
        cfg.setDeploymentMode(DeploymentMode.CONTINUOUS);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setSystemWorkerBlockedTimeout(120000);

        TransactionConfiguration transactionConfiguration = new TransactionConfiguration() ;
        transactionConfiguration.setTxTimeoutOnPartitionMapExchange(20000L);
        cfg.setTransactionConfiguration(transactionConfiguration) ;
        return cfg ;
    }

    public static Ignite getIgnite(){
        IgniteConfiguration cfg = getIgniteConfiguration() ;
        return Ignition.start(cfg) ;
    }

    public static Ignite getIgniteByXml(){
        return Ignition.start("node-config.xml") ;
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
