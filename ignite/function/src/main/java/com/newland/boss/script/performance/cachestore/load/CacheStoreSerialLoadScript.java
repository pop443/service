package com.newland.boss.script.performance.cachestore.load;

import com.newland.boss.entity.performance.cachestore.CacheStore1;
import com.newland.boss.entity.performance.cachestore.CacheStore1Configuration;
import com.newland.boss.entity.performance.cachestore.CacheStore2;
import com.newland.boss.entity.performance.cachestore.CacheStore2Configuration;
import com.newland.boss.script.features.BaseScript;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class CacheStoreSerialLoadScript extends BaseScript<String,CacheStore2> {
    public CacheStoreSerialLoadScript() {
        super(new CacheStore2Configuration());
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    @Override
    protected void work() {
        igniteCache.clear();
        long l1 = System.currentTimeMillis() ;
        igniteCache.loadCache(null);
        long l2 = System.currentTimeMillis() ;
        System.out.println("串行耗时："+(l2-l1));
    }

    public static void main(String[] args) throws Exception{
        CacheStoreSerialLoadScript scirpt = new CacheStoreSerialLoadScript() ;
        scirpt.start();
    }
}
