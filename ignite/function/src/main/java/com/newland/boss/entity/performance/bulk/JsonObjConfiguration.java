package com.newland.boss.entity.performance.bulk;

import com.fasterxml.jackson.databind.JsonNode;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/16.
 */
public class JsonObjConfiguration extends CustCacheConfiguration<String, String> {
    public JsonObjConfiguration() {
        super(String.class, String.class, 0);
        cacheName = "JSON";
    }

    @Override
    public CacheConfiguration<String, String> getCacheConfiguration() {
        CacheConfiguration<String, String> cacheConfiguration = super.getCacheConfiguration();
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setName("JSON") ;
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
