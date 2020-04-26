package com.newland.ignite.datasource.utils.boss;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xz on 2020/3/30.
 */
public class BossDataSourceUtil {
    public static void getDruidDataSource(Properties properties,Map<String, DruidDataSource> map) throws Exception {
        String db_auth_path = properties.getProperty("db_auth_path");

        for (Object obj : properties.keySet()) {
            String key = (String) obj;
            if (key.endsWith("name")) {
                String name = properties.getProperty(key);

                String driver = "oracle.jdbc.driver.OracleDriver";
                String url = "jdbc:oracle:thin:@10.32.229.52/palldb";
                String username = "bill";
                String password = "yrqczb2ayJKo28uQkbuM";
                String tns = "palldb";
                String init = "30";
                String minIdle = "30";
                String maxActive = "30";
                String testSql = "SELECT 'x' from dual";

                CNLDBConnectMgr.init(db_auth_path);
                System.out.println("-----------db_auth_path:"+db_auth_path);
                System.out.println("-----------name:"+name);
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
                    realPass = "E5Dson$4Wm7eB2c";
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
                map.put("mysql1",druidDataSource);
            }
        }
    }


}
