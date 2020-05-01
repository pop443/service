package com.newland.boss.entity.performance.cachestore;

import com.newland.ignite.cachestore.listen.DruidCacheStoreSessionListen;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.configuration.FactoryBuilder;

/**
 * Created by xz on 2020/3/6.
 */
public class CacheStore2Configuration extends CustCacheConfiguration<String,CacheStore2> {
    public CacheStore2Configuration() {
        super(String.class, CacheStore2.class);
    }

    @Override
    public CacheConfiguration<String, CacheStore2> getCacheConfiguration() {
        CacheConfiguration<String, CacheStore2> cachecfg = super.getCacheConfiguration() ;
        cachecfg.setCacheMode(CacheMode.PARTITIONED);
        cachecfg.setCacheStoreFactory(FactoryBuilder.factoryOf(CacheStore2Store.class));
        //session listen
        cachecfg.setCacheStoreSessionListenerFactories(FactoryBuilder.factoryOf(DruidCacheStoreSessionListen.class)) ;
        cachecfg.setReadThrough(true);
        cachecfg.setWriteThrough(true);

        cachecfg.setWriteBehindEnabled(true) ;

        cachecfg.setWriteBehindCoalescing(true) ;
        cachecfg.setWriteBehindBatchSize(10);
        cachecfg.setWriteBehindFlushThreadCount(10);
        cachecfg.setWriteBehindFlushSize(100) ;
        cachecfg.setWriteBehindFlushFrequency(3000) ;

        return cachecfg;
    }
}
