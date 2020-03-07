package com.newland.ignite.cachestore.entity;

import com.newland.ignite.cachestore.adapter.ExpiryCacheStore;
import com.newland.ignite.cachestore.adapter.UserInfoCacheStore;
import com.newland.ignite.cachestore.listen.DruidCacheStoreSessionListen;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.configuration.FactoryBuilder;

/**
 * Created by xz on 2020/3/6.
 */
public class UserInfoConfiguration extends CustCacheConfiguration<String,UserInfo> {
    public UserInfoConfiguration() {
        super(String.class, UserInfo.class);
    }

    @Override
    public CacheConfiguration<String, UserInfo> getCacheConfiguration() {
        CacheConfiguration<String, UserInfo> cachecfg = super.getCacheConfiguration() ;
        cachecfg.setCacheStoreFactory(FactoryBuilder.factoryOf(UserInfoCacheStore.class));
        //session listen
        cachecfg.setCacheStoreSessionListenerFactories(FactoryBuilder.factoryOf(DruidCacheStoreSessionListen.class)) ;
        cachecfg.setReadThrough(true);
        cachecfg.setWriteThrough(true);
        //后写
        //cachecfg.setWriteBehindEnabled(true);
        //cachecfg.setWriteBehindFlushSize(5120);
        //cachecfg.setWriteBehindFlushFrequency(5000);
        return cachecfg;
    }
}
