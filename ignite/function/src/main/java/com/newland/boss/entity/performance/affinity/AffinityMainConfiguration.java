package com.newland.boss.entity.performance.affinity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class AffinityMainConfiguration extends CustCacheConfiguration<String,AffinityMain> {
    public AffinityMainConfiguration() {
        super(String.class, AffinityMain.class);
    }

    @Override
    public CacheConfiguration<String, AffinityMain> getCacheConfiguration() {
        CacheConfiguration<String, AffinityMain> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(2);
        return cacheConfiguration;
    }
}
