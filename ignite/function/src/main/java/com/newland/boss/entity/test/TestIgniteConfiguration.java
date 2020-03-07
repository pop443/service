package com.newland.boss.entity.test;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class TestIgniteConfiguration extends CustCacheConfiguration<String,TestIgnite> {
    public TestIgniteConfiguration() {
        super(String.class, TestIgnite.class);
    }


    @Override
    public CacheConfiguration<String, TestIgnite> getCacheConfiguration() {
        CacheConfiguration<String, TestIgnite> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
