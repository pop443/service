package com.newland.boss.script.performance.cachestore;

import com.newland.boss.entity.performance.cachestore.CacheStore2;
import com.newland.boss.entity.performance.cachestore.CacheStore2Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class CacheStoreWBPutScript extends PerformanceScript<String,CacheStore2> {
    CacheStoreWBPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, CacheStore2>> cz) {
        super(new CacheStore2Configuration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("cachestore put："+enterParam.toString());
        CacheStoreWBPutScript scirpt = new CacheStoreWBPutScript(enterParam,CacheStoreWBPutScriptWork.class) ;
        scirpt.start();
    }
}
