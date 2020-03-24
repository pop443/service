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
public class Rebalance2Configuration extends CustCacheConfiguration<String,Rebalance2> {
    public Rebalance2Configuration() {
        super(String.class, Rebalance2.class);
    }

    @Override
    public CacheConfiguration<String, Rebalance2> getCacheConfiguration() {
        CacheConfiguration<String, Rebalance2> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.REPLICATED);
        cacheConfiguration.setBackups(1);
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC);
        cacheConfiguration.setStatisticsEnabled(true);
        return cacheConfiguration;
    }
}
