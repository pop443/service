package com.newland.boss.entity.performance.bulk;

import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/16.
 */
public class TextObjConfiguration {
    private String cacheName ;
    public TextObjConfiguration() {
        this.cacheName = "TEXTOBJ" ;
    }

    public CacheConfiguration<String, TextObj> getCacheConfiguration() {
        CacheConfiguration<String,TextObj> cacheConfiguration = new CacheConfiguration<>() ;
        cacheConfiguration.setName(cacheName) ;
        cacheConfiguration.setBackups(2) ;
        cacheConfiguration.setSqlSchema("newland") ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC);
        cacheConfiguration.setIndexedTypes( String.class ,TextObj.class ) ;
        return cacheConfiguration;
    }
}
