package com.newland.boss.entity.performance.bulk;

import com.fasterxml.jackson.databind.JsonNode;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/16.
 */
public class JsonObjConfiguration  {
    private String cacheName ;
    public JsonObjConfiguration() {
        this.cacheName = "JSONOBJ" ;
    }

    public CacheConfiguration<String, String> getCacheConfiguration() {
        CacheConfiguration<String,String> cacheConfiguration = new CacheConfiguration<>() ;
        cacheConfiguration.setName(cacheName) ;
        cacheConfiguration.setBackups(2) ;
        cacheConfiguration.setSqlSchema("newland") ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC);
        cacheConfiguration.setIndexedTypes( String.class ,String.class ) ;
        return cacheConfiguration;
    }
}
