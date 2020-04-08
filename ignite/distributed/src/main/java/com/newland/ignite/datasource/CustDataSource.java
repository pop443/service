package com.newland.ignite.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.newland.ignite.datasource.utils.DataSourceUtil;
import com.newland.ignite.datasource.utils.boss.BossDataSourceUtil;
import com.newland.ignite.utils.PropertiesHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xz on 2020/3/30.
 */
public class CustDataSource {
    private String[] paths ;
    private static Map<String,DruidDataSource> map = new HashMap<>();

    public CustDataSource(String[] paths) {
        this.paths = paths;
    }

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public void init(){
        dataSourceInstance();
    }

    public void close(){
        map.values().forEach(DruidDataSource::close);
    }

    private void dataSourceInstance(){
        if (paths.length==0){
            System.out.println("------------没有数据源");
            return;
        }
        for (String path : paths) {
            try {
                Properties properties = PropertiesHelper.getProperties(path);
                DataSourceUtil.getDruidDataSource(properties,map);
                System.out.println("------------加载:"+path+"完成");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("------------加载:异常");
            }
        }
    }

    public DruidDataSource getMap(String key) {
        return map.get(key);
    }
}
