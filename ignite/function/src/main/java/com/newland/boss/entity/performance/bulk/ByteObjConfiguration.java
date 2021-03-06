package com.newland.boss.entity.performance.bulk;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/16.
 */
public class ByteObjConfiguration  {
    private String cacheName ;
    public ByteObjConfiguration() {
        this.cacheName = "BYTEOBJ" ;
    }

    public CacheConfiguration<String, byte[]> getCacheConfiguration() {
        CacheConfiguration<String, byte[]> cacheConfiguration = new CacheConfiguration<>();
        cacheConfiguration.setName(cacheName) ;
        cacheConfiguration.setBackups(2) ;
        cacheConfiguration.setSqlSchema("newland") ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC);
        cacheConfiguration.setIndexedTypes( String.class ,byte[].class ) ;
        return cacheConfiguration;
    }
}
