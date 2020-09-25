package com.newland.boss.entity.transcation.aff;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class TranscationCacheAff1Configuration extends CustCacheConfiguration<TranscationCacheAff1Key,TranscationCacheAff1> {
    public TranscationCacheAff1Configuration() {
        super(TranscationCacheAff1Key.class, TranscationCacheAff1.class);
    }

    @Override
    public CacheConfiguration<TranscationCacheAff1Key, TranscationCacheAff1> getCacheConfiguration() {
        CacheConfiguration<TranscationCacheAff1Key, TranscationCacheAff1> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setBackups(0);
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL_SNAPSHOT);
        return cacheConfiguration;
    }
}
