package com.xz.ignite.basefunction.cachestore.mysql.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.xz.ignite.basefunction.query.jdbc.DBConfig;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

/**
 * Created by xz on 2020/2/6.
 */
public class MysqlConnection {
    public static Connection getConnection() throws SQLException {
        Properties properties = DBConfig.getProperties("cachestore-mysql.properties") ;
        String url = properties.getProperty("mysql.url") ;
        String username = properties.getProperty("mysql.username") ;
        String password = properties.getProperty("mysql.password") ;
        Connection connection = DriverManager.getConnection(url,username,password) ;
        connection.setAutoCommit(false);
        return connection ;
    }

    public static DataSource getDataSource() {
        Properties properties = DBConfig.getProperties("cachestore-mysql.properties") ;
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(properties.getProperty("mysql.url"));
        mysqlDataSource.setUser(properties.getProperty("mysql.username"));
        mysqlDataSource.setPassword(properties.getProperty("mysql.password"));
        return mysqlDataSource ;
    }


    public static void release(Connection conn){
        if (conn!=null){
            try {
                if (!conn.isClosed()){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void release(PreparedStatement pstm){
        if (pstm!=null){
            try {
                if (!pstm.isClosed()){
                    pstm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void release(ResultSet rs){
        if (rs!=null){
            try {
                if (!rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void release(ResultSet rs,PreparedStatement pstm,Connection conn){
        release(rs);
        release(pstm);
        release(conn);
    }


    public static void release(ResultSet rs, PreparedStatement pstm) {
        release(rs);
        release(pstm);
    }
}
