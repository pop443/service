package com.newland.boss.entity.transcation;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class TranscationCache2Configuration extends CustCacheConfiguration<String,TranscationCache2> {
    public TranscationCache2Configuration() {
        super(String.class, TranscationCache2.class);
    }

    @Override
    public CacheConfiguration<String, TranscationCache2> getCacheConfiguration() {
        CacheConfiguration<String, TranscationCache2> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setBackups(0);
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        return cacheConfiguration;
    }
}
