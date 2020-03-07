package com.newland.ignite.cachestore.entity;

import com.newland.ignite.cachestore.adapter.CourseCacheStore;
import com.newland.ignite.cachestore.listen.DruidCacheStoreSessionListen;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.configuration.FactoryBuilder;

/**
 * Created by xz on 2020/3/6.
 */
public class CourseConfiguration extends CustCacheConfiguration<String,Course> {
    public CourseConfiguration() {
        super(String.class, Course.class);
    }

    @Override
    public CacheConfiguration<String, Course> getCacheConfiguration() {
        CacheConfiguration<String, Course> cachecfg = super.getCacheConfiguration() ;
        cachecfg.setCacheStoreFactory(FactoryBuilder.factoryOf(CourseCacheStore.class));
        //session listen
        cachecfg.setCacheStoreSessionListenerFactories(FactoryBuilder.factoryOf(DruidCacheStoreSessionListen.class)) ;
        cachecfg.setReadThrough(true);
        cachecfg.setWriteThrough(true);

        return cachecfg;
    }
}
