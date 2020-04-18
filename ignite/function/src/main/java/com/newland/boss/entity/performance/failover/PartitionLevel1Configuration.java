package com.newland.boss.entity.performance.failover;

import com.newland.boss.entity.performance.obj.NearCustObj;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicyFactory;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class PartitionLevel1Configuration extends CustCacheConfiguration<String,PartitionLevel1> {
    public PartitionLevel1Configuration() {
        super(String.class, PartitionLevel1.class);
    }

    @Override
    public CacheConfiguration<String, PartitionLevel1> getCacheConfiguration() {
        CacheConfiguration<String, PartitionLevel1> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setBackups(1);
        cacheConfiguration.setBackups(1);
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC);
        cacheConfiguration.setRebalanceThrottle(0);
        cacheConfiguration.setRebalanceBatchSize(1* 1024 * 1024);
        cacheConfiguration.setStatisticsEnabled(true);
        return cacheConfiguration;
    }
}
