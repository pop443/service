package com.newland.boss.entity.performance.affinity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class AffinityItemNoConfiguration extends CustCacheConfiguration<String,AffinityItemNo> {
    public AffinityItemNoConfiguration() {
        super(String.class, AffinityItemNo.class);
    }

    @Override
    public CacheConfiguration<String, AffinityItemNo> getCacheConfiguration() {
        CacheConfiguration<String, AffinityItemNo> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
