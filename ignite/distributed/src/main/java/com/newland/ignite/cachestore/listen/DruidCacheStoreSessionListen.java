package com.newland.ignite.cachestore.listen;

import com.newland.ignite.datasource.CustDataSource;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.cache.store.CacheStoreSessionListener;
import org.apache.ignite.resources.LoggerResource;
import org.apache.ignite.resources.SpringResource;


/**
 * druid统一回话管理 数据源 transient
 */
public class DruidCacheStoreSessionListen implements CacheStoreSessionListener {

    @LoggerResource
    private IgniteLogger log;
    //@SpringResource(resourceName = "mysqlDataSource")
    @SpringResource(resourceName = "custDataSource")
    private transient CustDataSource custDataSource;


    public DruidCacheStoreSessionListen() {
        System.out.println("-------DruidListen-----init----");
    }

    @Override
    public void onSessionStart(CacheStoreSession ses) {
        log.info("--------------DruidListen onSessionStart");
        CacheConnHelper.getConnection(ses,custDataSource.getMap("mysql"));

    }

    @Override
    public void onSessionEnd(CacheStoreSession ses, boolean commit) {
        log.info("--------------DruidListen onSessionEnd:" + commit);
        CacheConnHelper.releaseConnection(ses,commit);
    }
}
