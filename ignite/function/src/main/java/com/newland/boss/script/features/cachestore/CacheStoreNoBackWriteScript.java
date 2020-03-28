package com.newland.boss.script.features.cachestore;

import com.newland.boss.script.features.BaseScript;
import com.newland.ignite.cachestore.entity.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 二级索引 cachestore
 */
public class CacheStoreNoBackWriteScript extends BaseScript<String,UserInfo> {
    public CacheStoreNoBackWriteScript() {
        super(new UserInfoConfiguration());
    }

    @Override
    protected void afterInitIgnite() {
        igniteCache = ignite.cache(cacheName) ;
        if (igniteCache!=null){
            igniteCache.removeAll();
            igniteCache.close();
        }
        ignite.destroyCache(cacheName);
    }

    @Override
    public void work() {
        int index = 10 ;
        Map<String,UserInfo> map = new HashMap<>() ;
        for (int i = 0; i < index; i++) {
            String key = i + "";
            UserInfo userInfo = new UserInfo(key, key, key,key);
            map.put(key, userInfo);
        }
        igniteCache.putAll(map);
    }

    public static void main(String[] args) {
        System.out.println("无后写 表名称：USERINFO ");
        CacheStoreNoBackWriteScript scirpt = new CacheStoreNoBackWriteScript() ;
        scirpt.start();
    }
}
