package com.newland.ignite.datasource.utils.boss;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by xz on 2020/3/30.
 */
public class BossDataSourceUtil {
    public static DruidDataSource getDruidDataSource(Properties properties) throws Exception {
        String db_auth_path = properties.getProperty("db_auth_path");
        String driver = properties.getProperty("mysql.driver");
        String url = properties.getProperty("mysql.url");
        String username = properties.getProperty("mysql.username");
        String password = properties.getProperty("mysql.password");
        String tns = properties.getProperty("mysql.tns");
        String init = properties.getProperty("mysql.pool.init");
        String minIdle = properties.getProperty("mysql.pool.minIdle");
        String maxActive = properties.getProperty("mysql.pool.maxActive");
        String testSql = properties.getProperty("mysql.testSql");

        CNLDBConnectMgr.init(db_auth_path);
        System.out.println("-----------db_auth_path:"+db_auth_path);
        System.out.println("-----------driver:"+driver);
        System.out.println("-----------url:"+url);
        System.out.println("-----------username:"+username);
        System.out.println("-----------password:"+password);
        System.out.println("-----------tns:"+tns);
        System.out.println("-----------init:"+init);
        System.out.println("-----------minIdle:"+minIdle);
        System.out.println("-----------maxActive:"+maxActive);
        System.out.println("-----------testSql:"+testSql);
        String realPass = CNLDBConnectMgr.getPasswd(2, tns, username, password);
        if(realPass == null)
        {
            realPass = properties.getProperty("mysql.realpass");
        }

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(realPass);
        druidDataSource.setInitialSize(Integer.parseInt(init));
        druidDataSource.setMinIdle(Integer.parseInt(minIdle));
        druidDataSource.setMaxActive(Integer.parseInt(maxActive));
        druidDataSource.setMaxWait(6000L);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000L);
        druidDataSource.setMinEvictableIdleTimeMillis(300000L);
        druidDataSource.setValidationQuery(testSql);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        try {
            druidDataSource.setFilters("stat");
            druidDataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }


}
