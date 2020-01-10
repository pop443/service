package com.xz.ignite.basefunction.query.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2019/11/26.
 */
public class DBConfig {

    private static Properties properties ;
    static{
        properties = new Properties() ;
        InputStream is = null ;
        try {
            is = DBConfig.class.getClassLoader().getResourceAsStream("igniteJDBC.properties");
            properties.load(is);
            String className = properties.getProperty("ignite.driver","org.apache.ignite.IgniteJdbcThinDriver") ;
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
