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
public class FusePartitionTwoConfiguration extends CustCacheConfiguration<String, FusePartitionTwo> {
    public FusePartitionTwoConfiguration() {
        super(String.class, FusePartitionTwo.class);
    }

    @Override
    public CacheConfiguration<String, FusePartitionTwo> getCacheConfiguration() {
        CacheConfiguration<String, FusePartitionTwo> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(1);
        cacheConfiguration.setPartitionLossPolicy(PartitionLossPolicy.READ_WRITE_SAFE) ;
        return cacheConfiguration;
    }
}
