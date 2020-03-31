package com.newland.ignite.datasource.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by xz on 2020/3/30.
 */
public class DataSourceUtil {
    public static DruidDataSource getDruidDataSource(Properties properties) {
        DruidDataSource druidDataSource = new DruidDataSource();
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
        return druidDataSource;
    }
}
