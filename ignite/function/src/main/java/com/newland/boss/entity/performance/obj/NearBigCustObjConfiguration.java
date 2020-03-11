package com.newland.boss.entity.performance.obj;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicyFactory;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class NearBigCustObjConfiguration extends CustCacheConfiguration<String,NearBigCustObj> {
    public NearBigCustObjConfiguration() {
        super(String.class, NearBigCustObj.class);
    }

    @Override
    public CacheConfiguration<String, NearBigCustObj> getCacheConfiguration() {
        CacheConfiguration<String, NearBigCustObj> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        NearCacheConfiguration<String, NearBigCustObj> nearCfg = new NearCacheConfiguration<>() ;
        nearCfg.setNearEvictionPolicyFactory(new LruEvictionPolicyFactory<>(10000)) ;
        cacheConfiguration.setNearConfiguration(nearCfg);
        return cacheConfiguration;
    }
}
