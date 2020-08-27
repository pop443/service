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
public class TranscationCache3Configuration extends CustCacheConfiguration<String,TranscationCache3> {
    public TranscationCache3Configuration() {
        super(String.class, TranscationCache3.class);
    }

    @Override
    public CacheConfiguration<String, TranscationCache3> getCacheConfiguration() {
        CacheConfiguration<String, TranscationCache3> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setBackups(2);
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        return cacheConfiguration;
    }
}
