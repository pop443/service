package com.newland.boss.entity.performance.failover;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class PartitionLevel3Configuration extends CustCacheConfiguration<String,PartitionLevel3> {
    public PartitionLevel3Configuration() {
        super(String.class, PartitionLevel3.class);
    }

    @Override
    public CacheConfiguration<String, PartitionLevel3> getCacheConfiguration() {
        CacheConfiguration<String, PartitionLevel3> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setBackups(1);
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC);
        cacheConfiguration.setRebalanceThrottle(0);
        cacheConfiguration.setRebalanceBatchSize(10* 1024 * 1024);
        cacheConfiguration.setStatisticsEnabled(true);
        return cacheConfiguration;
    }
}
