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
public class ListObjConfiguration extends CustCacheConfiguration<String,ListObj> {
    public ListObjConfiguration() {
        super(String.class, ListObj.class);
    }

    @Override
    public CacheConfiguration<String,ListObj> getCacheConfiguration() {
        CacheConfiguration<String,ListObj> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC) ;
        cacheConfiguration.setBackups(2);
        return cacheConfiguration;
    }
}
