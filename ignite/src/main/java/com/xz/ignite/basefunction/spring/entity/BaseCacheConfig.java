package com.xz.ignite.basefunction.spring.entity;

import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Created by xz on 2020/2/28.
 */
public abstract class BaseCacheConfig<K,V> {
    private Class<K> keyCz ;
    private Class<V> valueCz ;

    protected BaseCacheConfig(Class<K> keyCz, Class<V> valueCz) {
        this.keyCz = keyCz;
        this.valueCz = valueCz;
    }
    protected CacheConfiguration<K,V> getCacheConfig(){
        CacheConfiguration<K,V> cacheConfiguration = new CacheConfiguration<>();
        cacheConfiguration.setName(valueCz.getSimpleName()) ;
        //备份数量
        cacheConfiguration.setBackups(1) ;
        cacheConfiguration.setSqlSchema("spring") ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setIndexedTypes( keyCz ,valueCz) ;
        return cacheConfiguration ;
    }

}
