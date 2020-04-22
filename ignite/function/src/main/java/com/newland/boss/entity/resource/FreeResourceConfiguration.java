package com.newland.boss.entity.resource;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class FreeResourceConfiguration extends CustCacheConfiguration<String,FreeResource> {
    public FreeResourceConfiguration() {
        super(String.class, FreeResource.class);
    }

    @Override
    public CacheConfiguration<String, FreeResource> getCacheConfiguration() {
        CacheConfiguration<String, FreeResource> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setBackups(2);
        return cacheConfiguration;
    }
}
