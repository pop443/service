package com.xz.ignite.basefunction.cachestore.mysql.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.xz.ignite.basefunction.query.jdbc.DBConfig;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

/**
 * Created by xz on 2020/2/6.
 */
public class MysqlConnection {
    private static DataSource dataSource ;
    private static DataSource mysqlDataSource ;
    public static Connection getConnection() throws SQLException {
        Properties properties = DBConfig.getProperties("cachestore-mysql.properties") ;
        String url = properties.getProperty("mysql.url") ;
        String username = properties.getProperty("mysql.username") ;
        String password = properties.getProperty("mysql.password") ;
        Connection connection = DriverManager.getConnection(url,username,password) ;
        connection.setAutoCommit(false);
        return connection ;
    }

    public static DataSource getMysqlDataSource() {
        if (mysqlDataSource==null){
            Properties properties = DBConfig.getProperties("cachestore-mysql.properties") ;
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setURL(properties.getProperty("mysql.url"));
            dataSource.setUser(properties.getProperty("mysql.username"));
            dataSource.setPassword(properties.getProperty("mysql.password"));
            mysqlDataSource = dataSource ;
        }
        return mysqlDataSource ;
    }
    public static DataSource getDruidDataSource() {
        if (dataSource==null){
            Properties properties = DBConfig.getProperties("cachestore-mysql.properties") ;
            DruidDataSource druidDataSource = new DruidDataSource() ;
            druidDataSource.setDriverClassName(properties.getProperty("mysql.driver"));
            druidDataSource.setUrl(properties.getProperty("mysql.url"));
            druidDataSource.setUsername(properties.getProperty("mysql.username"));
            druidDataSource.setPassword(properties.getProperty("mysql.password"));
            druidDataSource.setInitialSize(Integer.parseInt(properties.getProperty("mysql.pool.init")));
            druidDataSource.setMinIdle(Integer.parseInt(properties.getProperty("mysql.pool.minIdle")));
            druidDataSource.setMaxActive(Integer.parseInt(properties.getProperty("mysql.pool.maxActive")));
            druidDataSource.setMaxWait(6000L);
            druidDataSource.setTimeBetweenEvictionRunsMillis(60000L);
            druidDataSource.setMinEvictableIdleTimeMillis(300000L);
            druidDataSource.setValidationQuery(properties.getProperty("mysql.testSql"));
            druidDataSource.setTestWhileIdle(true);
            druidDataSource.setTestOnBorrow(false);
            druidDataSource.setTestOnReturn(false);
            try {
                druidDataSource.setFilters("stat");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dataSource = druidDataSource ;
        }
        return dataSource ;
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
