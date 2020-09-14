package com.newland.ignite.multicenter.entity;

import com.newland.boss.entity.event.Event;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class MultiCenterObjConfiguration extends CustCacheConfiguration<String,MultiCenterObj> {
    public MultiCenterObjConfiguration() {
        super(String.class, MultiCenterObj.class);
    }

    @Override
    public CacheConfiguration<String, MultiCenterObj> getCacheConfiguration() {
        CacheConfiguration<String, MultiCenterObj> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(2);
        cacheConfiguration.setPartitionLossPolicy(PartitionLossPolicy.READ_WRITE_ALL) ;
        cacheConfiguration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE)) ;
        return cacheConfiguration;
    }
}
