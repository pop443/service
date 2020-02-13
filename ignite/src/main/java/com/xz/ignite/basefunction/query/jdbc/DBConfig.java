package com.xz.ignite.basefunction.query.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2019/11/26.
 */
public class DBConfig {

    private static Map<String,Properties> map = new HashMap<>() ;

    private static void load(String fileName){
        Properties properties = new Properties() ;
        InputStream is = null ;
        try {
            is = DBConfig.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(is);
            properties.forEach((k,v)->{
                String key = (String) k ;
                if (key.endsWith("driver")){
                    String value = (String) v ;
                    try {
                        Class.forName(value);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            map.put(fileName,properties);
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

    public static Properties getProperties(String fileName) {
        if (!map.containsKey(fileName)){
            load(fileName) ;
        }
        return map.get(fileName) ;
    }

}
