package com.newland.boss.entity.consistency;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class PartitionedModeConfiguration extends CustCacheConfiguration<String, PartitionedMode> {
    public PartitionedModeConfiguration() {
        super(String.class, PartitionedMode.class);
    }

    @Override
    public CacheConfiguration<String, PartitionedMode> getCacheConfiguration() {
        CacheConfiguration<String, PartitionedMode> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(1);
        return cacheConfiguration;
    }
}
