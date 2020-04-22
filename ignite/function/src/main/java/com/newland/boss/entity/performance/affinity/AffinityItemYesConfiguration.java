package com.newland.boss.entity.performance.affinity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class AffinityItemYesConfiguration extends CustCacheConfiguration<AffinityItemYesKey,AffinityItemYes> {
    public AffinityItemYesConfiguration() {
        super(AffinityItemYesKey.class, AffinityItemYes.class);
    }

    @Override
    public CacheConfiguration<AffinityItemYesKey, AffinityItemYes> getCacheConfiguration() {
        CacheConfiguration<AffinityItemYesKey, AffinityItemYes> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(2);
        return cacheConfiguration;
    }
}
