package com.newland.ignite.cachestore.entity;

import com.newland.ignite.cachestore.adapter.ExpiryCacheStore;
import com.newland.ignite.cachestore.listen.DruidCacheStoreSessionListen;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

/**
 * Created by xz on 2020/3/6.
 */
public class ExpiryConfiguration extends CustCacheConfiguration<String,Expiry> {
    public ExpiryConfiguration() {
        super(String.class, Expiry.class);
    }
    @Override
    public CacheConfiguration<String, Expiry> getCacheConfiguration() {
        CacheConfiguration<String, Expiry> cachecfg = super.getCacheConfiguration() ;
        cachecfg.setCacheStoreFactory(FactoryBuilder.factoryOf(ExpiryCacheStore.class));
        cachecfg.setCacheMode(CacheMode.PARTITIONED) ;
        //session listen
        cachecfg.setCacheStoreSessionListenerFactories(FactoryBuilder.factoryOf(DruidCacheStoreSessionListen.class)) ;
        cachecfg.setReadThrough(true);
        cachecfg.setWriteThrough(true);
        cachecfg.setWriteBehindEnabled(true);
        cachecfg.setWriteBehindFlushSize(5120);
        cachecfg.setWriteBehindFlushFrequency(5000);

        cachecfg.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
        cachecfg.setEagerTtl(false);
        return cachecfg;
    }
}
