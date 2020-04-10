package com.newland.boss.entity.fuse;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class FusePartitionOneConfiguration extends CustCacheConfiguration<String, FusePartitionOne> {
    public FusePartitionOneConfiguration() {
        super(String.class, FusePartitionOne.class);
    }

    @Override
    public CacheConfiguration<String, FusePartitionOne> getCacheConfiguration() {
        CacheConfiguration<String, FusePartitionOne> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        cacheConfiguration.setPartitionLossPolicy(PartitionLossPolicy.READ_WRITE_SAFE) ;
        return cacheConfiguration;
    }
}
