package com.newland.boss.entity.ep;

import com.newland.boss.entity.event.Event;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class EpDemoConfiguration extends CustCacheConfiguration<String,EpDemo> {
    public EpDemoConfiguration() {
        super(String.class, EpDemo.class);
    }

    @Override
    public CacheConfiguration<String, EpDemo> getCacheConfiguration() {
        CacheConfiguration<String, EpDemo> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
