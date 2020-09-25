package com.newland.boss.entity.performance.affinitykey;

import com.newland.boss.entity.performance.complex.ComplexKey;
import com.newland.boss.entity.performance.complex.ComplexValue;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/9/23.
 */
public class Cache1CacheConfiguration extends CustCacheConfiguration<Cache1Key,Cache1Value> {
    public Cache1CacheConfiguration() {
        super(Cache1Key.class, Cache1Value.class);
    }

    @Override
    public CacheConfiguration<Cache1Key,Cache1Value> getCacheConfiguration() {
        CacheConfiguration<Cache1Key,Cache1Value> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setSqlSchema("NEWLAND");
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC) ;
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
