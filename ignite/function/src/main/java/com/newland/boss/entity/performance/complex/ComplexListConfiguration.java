package com.newland.boss.entity.performance.complex;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class ComplexListConfiguration extends CustCacheConfiguration<String,ComplexList> {
    public ComplexListConfiguration() {
        super(String.class, ComplexList.class);
    }

    @Override
    public CacheConfiguration<String,ComplexList> getCacheConfiguration() {
        CacheConfiguration<String,ComplexList> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setSqlSchema("PUBLIC");
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC) ;
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
