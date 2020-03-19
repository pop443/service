package com.newland.boss.entity.performance.bulk;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/16.
 */
public class JavaObjConfiguration {
    private String cacheName ;
    public JavaObjConfiguration() {
        this.cacheName = "JAVAOBJ" ;
    }

    public CacheConfiguration<String, JavaObj> getCacheConfiguration() {
        CacheConfiguration<String, JavaObj> cacheConfiguration = new CacheConfiguration<>();
        cacheConfiguration.setName(cacheName) ;
        cacheConfiguration.setBackups(0) ;
        cacheConfiguration.setSqlSchema("newland") ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC);
        return cacheConfiguration;
    }
}
