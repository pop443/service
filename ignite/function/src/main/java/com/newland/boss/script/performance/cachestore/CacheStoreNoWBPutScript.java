package com.newland.boss.script.performance.cachestore;

import com.newland.boss.entity.performance.cachestore.CacheStore1;
import com.newland.boss.entity.performance.cachestore.CacheStore1Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 *
 */
public class CacheStoreNoWBPutScript extends PerformanceScript<String,CacheStore1> {
    CacheStoreNoWBPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, CacheStore1>> cz) {
        super(new CacheStore1Configuration(), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("cachestore put："+enterParam.toString());
        CacheStoreNoWBPutScript scirpt = new CacheStoreNoWBPutScript(enterParam,CacheStoreNoWBPutScriptWork.class) ;
        scirpt.start();
    }
}
