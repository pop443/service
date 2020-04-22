package com.newland.boss.entity.consistency;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class ReplicatedModeConfiguration extends CustCacheConfiguration<String,ReplicatedMode> {
    public ReplicatedModeConfiguration() {
        super(String.class, ReplicatedMode.class);
    }

    @Override
    public CacheConfiguration<String, ReplicatedMode> getCacheConfiguration() {
        CacheConfiguration<String, ReplicatedMode> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.REPLICATED);
        return cacheConfiguration;
    }
}
