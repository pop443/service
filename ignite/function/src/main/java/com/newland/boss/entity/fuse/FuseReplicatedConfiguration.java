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
public class FuseReplicatedConfiguration extends CustCacheConfiguration<String, FuseReplicated> {
    public FuseReplicatedConfiguration() {
        super(String.class, FuseReplicated.class);
    }

    @Override
    public CacheConfiguration<String, FuseReplicated> getCacheConfiguration() {
        CacheConfiguration<String, FuseReplicated> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.REPLICATED);
        cacheConfiguration.setBackups(0);
        cacheConfiguration.setPartitionLossPolicy(PartitionLossPolicy.READ_WRITE_SAFE) ;
        return cacheConfiguration;
    }
}
