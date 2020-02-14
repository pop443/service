package com.xz.ignite.basefunction.cachestore.mysql.custcachestore.sessionlisten;

import com.xz.ignite.basefunction.cachestore.mysql.config.MysqlConnection;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.cache.store.CacheStoreSessionListener;
import org.apache.ignite.internal.util.typedef.internal.U;
import org.apache.ignite.resources.LoggerResource;
import org.apache.ignite.resources.SpringResource;

import javax.cache.integration.CacheWriterException;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by xz on 2020/2/10.
 */
public class JdbcDruidCacheStoreSessionListen implements CacheStoreSessionListener {

    @LoggerResource
    private IgniteLogger log;

    @SpringResource(resourceName = "druidDataSource")
    private transient DataSource dataSource ;

    public JdbcDruidCacheStoreSessionListen() {
        System.out.println("-------DruidListen-----1----");
    }

    @Override
    public void onSessionStart(CacheStoreSession ses) {
        log.info("--------------DruidListen onSessionStart"+dataSource.getClass());
        if (ses.attachment() == null) {
            try {
                Connection conn = dataSource.getConnection();

                conn.setAutoCommit(false);

                ses.attach(conn);
            }
            catch (SQLException e) {
                throw new CacheWriterException("Failed to start store session [tx=" + ses.transaction() + ']', e);
            }
        }

    }

    @Override
    public void onSessionEnd(CacheStoreSession ses, boolean commit) {
        log.info("--------------DruidListen onSessionEnd:"+commit);
        Connection conn = ses.attach(null);
        if (conn != null) {

            try {
                if (commit){
                    conn.commit();
                }
                else{
                    conn.rollback();
                }
            }
            catch (SQLException e) {
                throw new CacheWriterException("Failed to end store session [tx=" + ses.transaction() + ']', e);
            }
            finally {
                U.closeQuiet(conn);
            }
        }
    }
}
