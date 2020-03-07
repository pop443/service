package com.newland.ignite.cachestore.listen;

import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.internal.util.typedef.internal.U;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        if (conn==null){
            conn = getConnection(dataSource);
            ses.attach(conn);
        }
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
        if (conn==null){
            System.out.println("链接为null");
            return;
        }
        try {
            if (!conn.isClosed()) {
                System.out.println("链接没关闭");
                if (commit) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            System.out.println("链接异常"+e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("链接关闭异常");
            }
            System.out.println("链接关闭");
        }
    }

}
