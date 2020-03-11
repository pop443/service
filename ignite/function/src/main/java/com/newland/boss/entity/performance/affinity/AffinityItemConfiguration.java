package com.newland.boss.entity.performance.affinity;

import com.newland.boss.entity.performance.obj.NearBigCustObj;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicyFactory;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class AffinityItemConfiguration extends CustCacheConfiguration<AffinityItemKey,AffinityItem> {
    public AffinityItemConfiguration() {
        super(AffinityItemKey.class, AffinityItem.class);
    }

    @Override
    public CacheConfiguration<AffinityItemKey, AffinityItem> getCacheConfiguration() {
        CacheConfiguration<AffinityItemKey, AffinityItem> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
