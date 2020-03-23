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
    protected Class<K> keyClass;
    protected Class<V> valueClass ;
    protected String cacheName ;
    protected IgniteCache<K,V> igniteCache ;
    protected IgniteDataStreamer<K,V> igniteDataStreamer ;
    protected int backups ;
    protected CacheRebalanceMode cacheRebalanceMode ;

    public CustCacheConfiguration(Class<K> keyClass, Class<V> valueClass) {
        //默认异步再平衡
        this(keyClass,valueClass,0,CacheRebalanceMode.ASYNC);
    }
    public CustCacheConfiguration(Class<K> keyClass, Class<V> valueClass,int backups) {
        this(keyClass,valueClass,backups,CacheRebalanceMode.ASYNC);
    }
    public CustCacheConfiguration(Class<K> keyClass, Class<V> valueClass,CacheRebalanceMode cacheRebalanceMode) {
        this(keyClass,valueClass,0,CacheRebalanceMode.ASYNC);
    }
    public CustCacheConfiguration(Class<K> keyClass, Class<V> valueClass,int backups,CacheRebalanceMode cacheRebalanceMode) {
        this.keyClass = keyClass;
        this.valueClass = valueClass;
        this.cacheName = valueClass.getSimpleName().toUpperCase();
        this.backups = backups ;
        this.cacheRebalanceMode = cacheRebalanceMode ;
    }

    public CacheConfiguration<K,V> getCacheConfiguration(){
        CacheConfiguration<K,V> cacheConfiguration = new CacheConfiguration<>() ;
        cacheConfiguration.setName(cacheName) ;
        cacheConfiguration.setBackups(backups) ;
        cacheConfiguration.setSqlSchema("newland") ;
        cacheConfiguration.setCacheMode(CacheMode.REPLICATED) ;
        cacheConfiguration.setRebalanceMode(cacheRebalanceMode);
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
        }

        return igniteDataStreamer;
    }

}
