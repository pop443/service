package com.newland.boss.entity.performance.rebalance;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class Rebalance3Configuration extends CustCacheConfiguration<String,Rebalance3> {
    public Rebalance3Configuration() {
        super(String.class, Rebalance3.class);
    }

    @Override
    public CacheConfiguration<String, Rebalance3> getCacheConfiguration() {
        CacheConfiguration<String, Rebalance3> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(2);
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.SYNC);
        cacheConfiguration.setStatisticsEnabled(true);
        return cacheConfiguration;
    }
}
