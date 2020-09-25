package com.newland.boss.entity.performance.affinitykey;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/9/23.
 */
public class Cache2CacheConfiguration extends CustCacheConfiguration<Cache2Key,Cache2Value> {
    public Cache2CacheConfiguration() {
        super(Cache2Key.class, Cache2Value.class);
    }

    @Override
    public CacheConfiguration<Cache2Key,Cache2Value> getCacheConfiguration() {
        CacheConfiguration<Cache2Key,Cache2Value> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setSqlSchema("NEWLAND");
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC) ;
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
