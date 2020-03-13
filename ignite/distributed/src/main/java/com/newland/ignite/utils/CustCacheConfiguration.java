package com.newland.ignite.utils;

import com.newland.ignite.continusquery.entity.ListenEntity;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by Administrator on 2019/11/27.
 */
public abstract class CustCacheConfiguration<K,V> {
    private Class<K> keyClass;
    private Class<V> valueClass ;
    private String cacheName ;
    private IgniteCache<K,V> igniteCache ;
    private IgniteDataStreamer<K,V> igniteDataStreamer ;

    public CustCacheConfiguration(Class<K> keyClass, Class<V> valueClass) {
        this.keyClass = keyClass;
        this.valueClass = valueClass;
        this.cacheName = valueClass.getSimpleName().toUpperCase();
    }

    public CacheConfiguration<K,V> getCacheConfiguration(){
        CacheConfiguration<K,V> cacheConfiguration = new CacheConfiguration<>() ;
        cacheConfiguration.setName(cacheName) ;
        cacheConfiguration.setBackups(0) ;
        cacheConfiguration.setSqlSchema("newland") ;
        cacheConfiguration.setCacheMode(CacheMode.REPLICATED) ;
        cacheConfiguration.setIndexedTypes( keyClass ,valueClass ) ;
        return cacheConfiguration ;
    }

    public IgniteCache<K,V> getIgniteCache(Ignite ignite){
        if (igniteCache==null){
            igniteCache = ignite.cache(cacheName) ;
            if (igniteCache==null){
                igniteCache = ignite.createCache(getCacheConfiguration());
            }
        }
        return igniteCache ;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void close(){
        if (igniteCache!=null){
            igniteCache.close();
        }
        if (igniteDataStreamer!=null){
            igniteDataStreamer.close();
        }
    }

    public IgniteDataStreamer<K,V> getDataStreamer(Ignite ignite) {
        if (igniteDataStreamer==null){
            try {
                igniteDataStreamer = ignite.dataStreamer(cacheName);
            } catch (IllegalStateException e) {
                this.getIgniteCache(ignite);
            }
            if (igniteDataStreamer==null){
                igniteDataStreamer = ignite.dataStreamer(cacheName) ;
            }
        }

        return igniteDataStreamer;
    }

}