package com.newland.ignite.query.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.newland.ignite.query.jdbc.IgniteConnection;
import com.newland.ignite.utils.ConnectionUtil;
import com.newland.ignite.utils.PropertiesConfig;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

/**
 * Created by xz on 2020/2/6.
 */
public class MysqlConnection extends ConnectionUtil {
    private static DataSource dataSource ;
    private static DataSource mysqlDataSource ;
    private static Properties properties = PropertiesConfig.getProperties("mysql.properties");

    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("mysql1.url") ;
        String username = properties.getProperty("mysql1.username") ;
        String password = properties.getProperty("mysql1.password") ;
        Connection connection = DriverManager.getConnection(url,username,password) ;
        connection.setAutoCommit(false);
        return connection ;
    }

    public static DataSource getMysqlDataSource() {
        if (mysqlDataSource==null){
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
                druidDataSource.init();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dataSource = druidDataSource ;

        }
        return dataSource ;
    }
    public static void main(String[] args) throws SQLException{
        Connection conn = MysqlConnection.getDruidDataSource().getConnection() ;
        PreparedStatement pstm = conn.prepareStatement("select 1,2");
        ResultSet rs = pstm.executeQuery() ;
        while (rs.next()){
            System.out.println(rs.getInt(1)+"--"+rs.getInt(2));
        }
        ConnectionUtil.release(rs,pstm,conn);
    }


}
