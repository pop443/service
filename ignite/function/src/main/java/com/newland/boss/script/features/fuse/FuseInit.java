package com.newland.boss.script.features.fuse;

import com.newland.boss.entity.fuse.*;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * 测试的脚本
 */
public class FuseInit {
    private Ignite ignite ;
    private IgniteCache<String,FuseReplicated> fuseReplicatedIgniteCache ;
    private IgniteCache<String,FusePartitionOne> fusePartitionOneIgniteCache ;
    private IgniteCache<String,FusePartitionTwo> fusePartitionTwoIgniteCache ;

    public FuseInit() {
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,FuseReplicated>  cfg1 = new FuseReplicatedConfiguration().getCacheConfiguration();
        CacheConfiguration<String,FusePartitionOne> cfg2 = new FusePartitionOneConfiguration().getCacheConfiguration();
        CacheConfiguration<String,FusePartitionTwo> cfg3 = new FusePartitionTwoConfiguration().getCacheConfiguration();
        ignite.destroyCache(cfg1.getName());
        ignite.destroyCache(cfg2.getName());
        ignite.destroyCache(cfg3.getName());
        fuseReplicatedIgniteCache = ignite.createCache(cfg1) ;
        fusePartitionOneIgniteCache = ignite.createCache(cfg2) ;
        fusePartitionTwoIgniteCache = ignite.createCache(cfg3) ;
    }

    public void start() {
        for (int i = 0; i < 3; i++) {
            String key = i+"" ;
            FuseReplicated fuseReplicated = new FuseReplicated(key,key,i) ;
            FusePartitionOne fusePartitionOne = new FusePartitionOne(key,key,i) ;
            FusePartitionTwo fusePartitionTwo = new FusePartitionTwo(key,key,i) ;
            fuseReplicatedIgniteCache.put(fuseReplicated.getId(),fuseReplicated);
            fusePartitionOneIgniteCache.put(fusePartitionOne.getId(),fusePartitionOne);
            fusePartitionTwoIgniteCache.put(fusePartitionTwo.getId(),fusePartitionTwo);
        }
        destory();
    }

    private void destory() {
        fuseReplicatedIgniteCache.close();
        fusePartitionOneIgniteCache.close();
        fusePartitionTwoIgniteCache.close();
        ignite.close();

    }

    public static void main(String[] args) {
        FuseInit scirpt = new FuseInit() ;
        scirpt.start();
    }

}
