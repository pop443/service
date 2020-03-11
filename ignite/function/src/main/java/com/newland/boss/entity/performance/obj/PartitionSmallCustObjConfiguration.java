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
public class PartitionSmallCustObjConfiguration extends CustCacheConfiguration<String,PartitionSmallCustObj> {
    public PartitionSmallCustObjConfiguration() {
        super(String.class, PartitionSmallCustObj.class);
    }

    @Override
    public CacheConfiguration<String, PartitionSmallCustObj> getCacheConfiguration() {
        CacheConfiguration<String, PartitionSmallCustObj> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
