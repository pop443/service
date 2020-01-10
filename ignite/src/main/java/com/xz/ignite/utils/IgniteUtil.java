package com.xz.ignite.utils;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.zk.ZookeeperDiscoverySpi;

/**
 * Created by Administrator on 2019/12/25.
 */
public class IgniteUtil {
    private static Ignite ignite ;
    static{
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

        ignite = Ignition.start(cfg);
    }

    public static Ignite getIgnite(){
        return ignite ;
    }

    public static void release(Ignite ignite){
        ignite.close();
    }

    public static <T> BinaryObject toBinary(T t){
        return Ignition.ignite().binary().toBinary(t) ;
    }
}
