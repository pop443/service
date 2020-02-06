package com.xz.ignite.basefunction.cachestore.mysql.config;

import com.xz.ignite.basefunction.query.jdbc.DBConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by xz on 2020/2/5.
 */
public class MysqlConfig {
    private static Properties properties ;
    static{
        properties = new Properties() ;
        InputStream is = null ;
        try {
            is = DBConfig.class.getClassLoader().getResourceAsStream("cachestore-mysql.properties");
            properties.load(is);
            String className = properties.getProperty("mysql.driver") ;
            Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("--------------"+e.getMessage()+"----------------");
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Properties getProperties() {
        return properties;
    }
}
