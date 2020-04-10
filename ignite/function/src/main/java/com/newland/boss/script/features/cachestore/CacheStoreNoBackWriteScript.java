package com.newland.boss.script.features.cachestore;

import com.newland.boss.script.features.BaseScript;
import com.newland.ignite.cachestore.entity.*;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * 二级索引 cachestore
 */
public class CacheStoreNoBackWriteScript  {
    private Ignite ignite ;
    private IgniteCache<String,UserInfo> igniteCache1;
    public CacheStoreNoBackWriteScript() {
        ignite = IgniteUtil.getIgniteByXml("node-config-manyDS.xml") ;
        CacheConfiguration<String,UserInfo> cacheConfiguration1 = new UserInfoConfiguration().getCacheConfiguration() ;
        ignite.destroyCache(cacheConfiguration1.getName());
        igniteCache1 = ignite.createCache(cacheConfiguration1);
    }


    public void start() {
        int index = 10 ;
        Map<String,UserInfo> map = new HashMap<>() ;
        for (int i = 0; i < index; i++) {
            String key = i + "";
            UserInfo userInfo = new UserInfo(key, key, key,key);
            map.put(key, userInfo);
        }
        igniteCache1.putAll(map);
        destory();
    }

    private void destory() {
        igniteCache1.close();
        ignite.close();
    }


    public static void main(String[] args) {
        System.out.println("无后写 表名称：USERINFO ");
        CacheStoreNoBackWriteScript scirpt = new CacheStoreNoBackWriteScript() ;
        scirpt.start();
    }
}
