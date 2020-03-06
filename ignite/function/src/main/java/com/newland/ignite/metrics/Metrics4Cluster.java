package com.newland.ignite.metrics;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheMetrics;
import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.internal.IgnitionEx;
import org.apache.ignite.transactions.TransactionMetrics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 2020/1/16.
 */
public class Metrics4Cluster {
    Ignite ignite = null ;
    @Before
    public void before(){
        ignite = IgniteUtil.getIgniteByXml();
    }
    /**
     * 集群 metrics
     */
    @Test
    public void clusterMetrics(){
        ClusterMetrics clusterMetrics = ignite.cluster().metrics() ;
        System.out.println(clusterMetrics.getAverageCpuLoad());
    }
    /**5
     * 各个 server metrics
     */
    @Test
    public void localMetrics(){
        ignite.cluster().nodes().forEach(clusterNode -> {
            if (!clusterNode.isClient()) {
                System.out.println(clusterNode.metrics().getAverageCpuLoad());
            }
        });
    }

    /**
     * 缓存metrics
     */
    @Test
    public void cacheMetrics(){
        IgniteCache<String,String> igniteCache = ignite.cache("XZ") ;
        igniteCache.enableStatistics(true);
        CacheMetrics cacheMetrics = igniteCache.metrics() ;
        CacheMetrics localCacheMetrics = igniteCache.localMetrics() ;
        System.out.println(cacheMetrics.getAverageGetTime());
        System.out.println(localCacheMetrics.getAverageGetTime());
    }

    /**
     * 事务metrics
     */
    @Test
    public void TransactionsMetrics(){
        TransactionMetrics transactionMetrics = ignite.transactions().metrics() ;
        System.out.println(transactionMetrics.getTransactionsCommittedNumber());
    }

    /**
     * Ignition
     */
    @Test
    public void Ignition(){
        System.out.println(IgnitionEx.state());
    }


    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
