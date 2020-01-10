package com.xz.ignite.utils;

import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by Administrator on 2019/11/27.
 */
public class CacheConfigurationUtil {

    public static <K,V>  CacheConfiguration getNoPersistenceConfig(Class<K> keyClass, Class<V> valueClass){
        CacheConfiguration<K,V> cacheConfiguration = new CacheConfiguration<>() ;
        cacheConfiguration.setName(valueClass.getSimpleName().toUpperCase()) ;
        //备份数量
        cacheConfiguration.setBackups(1) ;
        //缓存组 映射关系内存共享
        //cacheConfiguration.setGroupName("group1") ;
        cacheConfiguration.setSqlSchema("label") ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setDataRegionName("nopersistence_Region") ;
        cacheConfiguration.setIndexedTypes( keyClass ,valueClass ) ;
        //默认为异步的再平衡
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC) ;
        return cacheConfiguration ;
    }

    public static <K,V>  CacheConfiguration getPersistenceConfig(Class<K> keyClass, Class<V> valueClass){
        CacheConfiguration<K,V> cacheConfiguration = new CacheConfiguration<>() ;
        cacheConfiguration.setName(valueClass.getSimpleName().toUpperCase()) ;
        //备份数量
        cacheConfiguration.setBackups(1) ;
        //缓存组 映射关系内存共享
        //cacheConfiguration.setGroupName("group1") ;
        cacheConfiguration.setSqlSchema("label") ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setIndexedTypes( keyClass ,valueClass ) ;
        //默认为异步的再平衡
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC) ;
        cacheConfiguration.setRebalanceBatchSize(512 * 1024) ;
        //100 ms 再平衡间隔
        cacheConfiguration.setRebalanceThrottle(100) ;
        return cacheConfiguration ;
    }


}
