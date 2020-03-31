package com.newland.ignite.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xz on 2020/3/30.
 */
public class PropertiesHelper {
    private static Map<String,Properties> map = new HashMap<>() ;

    private static void load(String fileName){
        Properties properties = new Properties() ;
        InputStream is = null ;
        try {
            is = new FileInputStream(fileName);
            properties.load(is);
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
