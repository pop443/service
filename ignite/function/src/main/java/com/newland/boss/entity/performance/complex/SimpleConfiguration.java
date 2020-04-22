package com.newland.boss.entity.performance.complex;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class SimpleConfiguration extends CustCacheConfiguration<String,SimpleValue> {
    public SimpleConfiguration() {
        super(String.class, SimpleValue.class);
    }

    @Override
    public CacheConfiguration<String,SimpleValue> getCacheConfiguration() {
        CacheConfiguration<String,SimpleValue> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL) ;
        cacheConfiguration.setBackups(2);
        return cacheConfiguration;
    }
}
