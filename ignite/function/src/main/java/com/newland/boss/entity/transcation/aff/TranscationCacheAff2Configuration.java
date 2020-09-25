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
public class TranscationCacheAff2Configuration extends CustCacheConfiguration<String, TranscationCacheAff2> {
    public TranscationCacheAff2Configuration() {
        super(String.class, TranscationCacheAff2.class);
    }

    @Override
    public CacheConfiguration<String, TranscationCacheAff2> getCacheConfiguration() {
        CacheConfiguration<String, TranscationCacheAff2> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setBackups(2);
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL_SNAPSHOT);
        return cacheConfiguration;
    }
}
