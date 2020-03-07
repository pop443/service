package com.newland.boss.entity.expired;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class ExpiredConfiguration extends CustCacheConfiguration<String,Expired> {
    public ExpiredConfiguration() {
        super(String.class, Expired.class);
    }

    @Override
    public CacheConfiguration<String, Expired> getCacheConfiguration() {
        CacheConfiguration<String, Expired> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setBackups(1);
        cacheConfiguration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS,20))) ;
        return cacheConfiguration;
    }
}
