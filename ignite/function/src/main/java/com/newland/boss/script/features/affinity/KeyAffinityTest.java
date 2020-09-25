package com.newland.boss.script.features.affinity;

import com.newland.boss.entity.performance.affinity.AffinityItemYes;
import com.newland.boss.entity.performance.affinity.AffinityItemYesConfiguration;
import com.newland.boss.entity.performance.affinity.AffinityItemYesKey;
import com.newland.boss.entity.performance.affinitykey.*;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xz on 2020/9/23.
 */
public class KeyAffinityTest {
    private Ignite ignite ;
    private IgniteCache<Cache1Key, Cache1Value> igniteCache1 ;
    private Affinity<Cache1Key> affinity1 ;
    private IgniteCache<Cache2Key, Cache2Value> igniteCache2 ;
    private Affinity<Cache2Key> affinity2 ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<Cache1Key, Cache1Value> cfg1 = new Cache1CacheConfiguration().getCacheConfiguration() ;
        igniteCache1 = ignite.getOrCreateCache(cfg1) ;
        affinity1 = ignite.affinity(cfg1.getName()) ;

        CacheConfiguration<Cache2Key, Cache2Value> cfg2 = new Cache2CacheConfiguration().getCacheConfiguration() ;
        igniteCache2 = ignite.getOrCreateCache(cfg2) ;
        affinity2 = ignite.affinity(cfg2.getName()) ;
    }

    @Test
    public void putData(){
        for (int i = 0; i < 10; i++) {
            Cache1Key key = new Cache1Key("A"+i,"B") ;
            Cache1Value value = new Cache1Value(i+"",i+"") ;
            igniteCache1.put(key,value);

            Cache2Key key2 = new Cache2Key("A"+i,"B") ;
            Cache2Value value2 = new Cache2Value(i+"",i+"") ;
            igniteCache2.put(key2,value2);
        }

    }

    @Test
    public void getDataAffinity(){
        for (int i = 0; i < 10; i++) {
            Cache1Key key = new Cache1Key("A"+i,"B") ;
            ClusterNode clusterNode = affinity1.mapKeyToNode(key) ;
            System.out.println(clusterNode.consistentId().toString());
        }
        System.out.println("-------------------------------");
        for (int i = 0; i < 10; i++) {
            Cache2Key key = new Cache2Key("A"+i,"B") ;
            ClusterNode clusterNode = affinity2.mapKeyToNode(key) ;
            System.out.println(clusterNode.consistentId().toString());
        }
    }



    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
