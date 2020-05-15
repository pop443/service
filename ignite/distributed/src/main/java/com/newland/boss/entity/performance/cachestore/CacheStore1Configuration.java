package com.newland.boss.entity.performance.cachestore;

import com.newland.ignite.cachestore.listen.DruidCacheStoreSessionListen;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.configuration.FactoryBuilder;

/**
 * Created by xz on 2020/3/6.
 */
public class CacheStore1Configuration extends CustCacheConfiguration<String,CacheStore1> {
    public CacheStore1Configuration() {
        super(String.class, CacheStore1.class);
    }

    @Override
    public CacheConfiguration<String, CacheStore1> getCacheConfiguration() {
        CacheConfiguration<String, CacheStore1> cachecfg = super.getCacheConfiguration() ;
        cachecfg.setCacheMode(CacheMode.PARTITIONED);
        cachecfg.setCacheStoreFactory(FactoryBuilder.factoryOf(CacheStore1Store.class));
        //session listen
        cachecfg.setCacheStoreSessionListenerFactories(FactoryBuilder.factoryOf(DruidCacheStoreSessionListen.class)) ;
        cachecfg.setReadThrough(true);
        cachecfg.setWriteThrough(true);

        //后写配置
        cachecfg.setWriteBehindEnabled(true) ;


        cachecfg.setWriteBehindBatchSize(512);
        cachecfg.setWriteBehindCoalescing(true) ;
        cachecfg.setWriteBehindFlushThreadCount(10);
        cachecfg.setWriteBehindFlushSize(10240) ;
        cachecfg.setWriteBehindFlushFrequency(3000) ;
        return cachecfg;
    }
}
