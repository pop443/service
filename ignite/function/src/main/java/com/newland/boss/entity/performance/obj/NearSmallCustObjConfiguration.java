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
public class NearSmallCustObjConfiguration extends CustCacheConfiguration<String,NearSmallCustObj> {
    public NearSmallCustObjConfiguration() {
        super(String.class, NearSmallCustObj.class,0);
    }

    public NearSmallCustObjConfiguration(int backups) {
        super(String.class, NearSmallCustObj.class, backups);
    }

    @Override
    public CacheConfiguration<String, NearSmallCustObj> getCacheConfiguration() {
        CacheConfiguration<String, NearSmallCustObj> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setBackups(backups);
        NearCacheConfiguration<String, NearSmallCustObj> nearCfg = new NearCacheConfiguration<>() ;
        nearCfg.setNearEvictionPolicyFactory(new LruEvictionPolicyFactory<>(10000)) ;
        cacheConfiguration.setNearConfiguration(nearCfg);
        return cacheConfiguration;
    }
}
