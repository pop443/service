package com.newland.boss.script.features.cachestore;

import com.newland.boss.script.features.BaseScript;
import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.cachestore.entity.ExpiryConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * 二级索引 cachestore
 */
public class CacheStoreBackWriteScript extends BaseScript<String,Expiry> {
    public CacheStoreBackWriteScript() {
        super(new ExpiryConfiguration());
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
        Map<String,Expiry> map = new HashMap<>() ;
        for (int i = 0; i < index; i++) {
            String key = i + "";
            Expiry expiry = new Expiry(key, key, key, new Automation(key, i, key));
            map.put(key, expiry);

        }
        igniteCache.putAll(map);

    }

    public static void main(String[] args) {
        System.out.println("后写 表名称：EXPIRY ");
        CacheStoreBackWriteScript scirpt = new CacheStoreBackWriteScript() ;
        scirpt.start();
    }
}
