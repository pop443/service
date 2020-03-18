package com.newland.boss.entity.event;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class EventConfiguration extends CustCacheConfiguration<String,Event> {
    public EventConfiguration() {
        super(String.class, Event.class);
    }

    @Override
    public CacheConfiguration<String, Event> getCacheConfiguration() {
        CacheConfiguration<String, Event> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        cacheConfiguration.setPartitionLossPolicy(PartitionLossPolicy.READ_WRITE_ALL) ;
        return cacheConfiguration;
    }
}
