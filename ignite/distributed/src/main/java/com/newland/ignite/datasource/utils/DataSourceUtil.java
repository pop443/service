package com.newland.ignite.datasource.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xz on 2020/3/30.
 */
public class DataSourceUtil {
    public static void getDruidDataSource(Properties properties, Map<String, DruidDataSource> map) {
        try {
            for (Object obj : properties.keySet()) {
                String key = (String) obj;
                if (key.endsWith(".name")) {
                    String name = properties.getProperty(key);

                    DruidDataSource druidDataSource = new DruidDataSource();
                    druidDataSource.setDriverClassName(properties.getProperty(name + ".driver"));
                    druidDataSource.setUrl(properties.getProperty(name + ".url"));
                    druidDataSource.setUsername(properties.getProperty(name + ".username"));
                    druidDataSource.setPassword(properties.getProperty(name + ".password"));
                    druidDataSource.setInitialSize(Integer.parseInt(properties.getProperty(name + ".pool.init")));
                    druidDataSource.setMinIdle(Integer.parseInt(properties.getProperty(name + ".pool.minIdle")));
                    druidDataSource.setMaxActive(Integer.parseInt(properties.getProperty(name + ".pool.maxActive")));
                    druidDataSource.setMaxWait(6000L);
                    druidDataSource.setTimeBetweenEvictionRunsMillis(60000L);
                    druidDataSource.setMinEvictableIdleTimeMillis(300000L);
                    druidDataSource.setValidationQuery(properties.getProperty(name + ".testSql"));
                    druidDataSource.setTestWhileIdle(true);
                    druidDataSource.setTestOnBorrow(false);
                    druidDataSource.setTestOnReturn(false);
                    druidDataSource.setTimeBetweenLogStatsMillis(Long.parseLong(properties.getProperty(name + ".timeBetweenLogStatsMillis")));

                    druidDataSource.setFilters(properties.getProperty(name + ".filters"));
                    druidDataSource.init();
                    map.put(name,druidDataSource);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
