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
public class TranscationCache4Configuration extends CustCacheConfiguration<String,TranscationCache4> {
    public TranscationCache4Configuration() {
        super(String.class, TranscationCache4.class);
    }

    @Override
    public CacheConfiguration<String, TranscationCache4> getCacheConfiguration() {
        CacheConfiguration<String, TranscationCache4> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setBackups(2);
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        return cacheConfiguration;
    }
}
