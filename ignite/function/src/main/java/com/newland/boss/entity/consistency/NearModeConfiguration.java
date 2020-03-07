package com.newland.boss.entity.consistency;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicyFactory;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;

/**
 * Created by xz on 2020/3/2.
 */
public class NearModeConfiguration extends CustCacheConfiguration<String, NearMode> {
    public NearModeConfiguration() {
        super(String.class, NearMode.class);
    }

    @Override
    public CacheConfiguration<String, NearMode> getCacheConfiguration() {
        CacheConfiguration<String, NearMode> cachecfg = super.getCacheConfiguration() ;
        NearCacheConfiguration<String, NearMode> nearCfg = new NearCacheConfiguration<>() ;
        nearCfg.setNearEvictionPolicyFactory(new LruEvictionPolicyFactory<>(10)) ;
        cachecfg.setNearConfiguration(nearCfg);
        return cachecfg;
    }
}
