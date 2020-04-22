package com.newland.boss.entity.performance.obj;

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
public class NearCustObjConfiguration extends CustCacheConfiguration<String,NearCustObj> {
    public NearCustObjConfiguration() {
        super(String.class, NearCustObj.class);
    }

    @Override
    public CacheConfiguration<String, NearCustObj> getCacheConfiguration() {
        CacheConfiguration<String, NearCustObj> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(2);
        NearCacheConfiguration<String, NearCustObj> nearCfg = new NearCacheConfiguration<>() ;
        nearCfg.setNearEvictionPolicyFactory(new LruEvictionPolicyFactory<>(10000)) ;
        cacheConfiguration.setNearConfiguration(nearCfg);
        return cacheConfiguration;
    }
}
