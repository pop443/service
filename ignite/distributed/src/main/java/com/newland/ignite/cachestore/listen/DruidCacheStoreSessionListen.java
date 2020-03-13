package com.newland.ignite.cachestore.listen;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.cache.store.CacheStoreSessionListener;
import org.apache.ignite.resources.LoggerResource;
import org.apache.ignite.resources.SpringResource;

import javax.sql.DataSource;

/**
 * druid统一回话管理 数据源 transient
 */
public class DruidCacheStoreSessionListen implements CacheStoreSessionListener {

    @LoggerResource
    private IgniteLogger log;
    //@SpringResource(resourceName = "mysqlDataSource")
    @SpringResource(resourceName = "druidDataSource")
    private transient DataSource dataSource;

    public DruidCacheStoreSessionListen() {
        System.out.println("-------DruidListen-----init----");
    }

    @Override
    public void onSessionStart(CacheStoreSession ses) {
        log.info("--------------DruidListen onSessionStart");
        CacheConnHelper.getConnection(ses,dataSource);

    }

    @Override
    public void onSessionEnd(CacheStoreSession ses, boolean commit) {
        log.info("--------------DruidListen onSessionEnd:" + commit);
        CacheConnHelper.releaseConnection(ses,commit);
    }
}