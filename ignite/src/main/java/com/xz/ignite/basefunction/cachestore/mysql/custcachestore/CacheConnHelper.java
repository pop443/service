package com.xz.ignite.basefunction.cachestore.mysql.custcachestore;

import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.internal.util.typedef.internal.U;

import javax.cache.integration.CacheWriterException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by xz on 2020/2/25.
 */
public class CacheConnHelper {

    /**
     * 封装cachestore上下文数据库链接
     * @param ses
     * @param dataSource
     * @return
     */
    public static void getConnection(CacheStoreSession ses,DataSource dataSource){
        Connection conn = ses.attachment() ;
        if (conn!=null){
            System.out.println("已经有连接了");
        }
        conn = getConnection(dataSource);
        ses.attach(conn);
    }

    public static Connection getConnection(DataSource dataSource){
        Connection conn = null ;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn ;
    }

    public static void releaseConnection(CacheStoreSession ses,boolean commit){
        Connection conn = ses.attachment();
        try {
            if (conn != null && !conn.isClosed()) {
                if (commit) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            U.closeQuiet(conn);
        }
    }
}
