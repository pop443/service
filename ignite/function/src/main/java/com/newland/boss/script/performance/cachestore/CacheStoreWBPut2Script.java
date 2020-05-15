package com.newland.boss.script.performance.cachestore;

import com.newland.boss.entity.performance.cachestore.CacheStore1;
import com.newland.boss.entity.performance.cachestore.CacheStore1Configuration;
import com.newland.boss.entity.performance.cachestore.CacheStore2;
import com.newland.boss.entity.performance.cachestore.CacheStore2Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class CacheStoreWBPut2Script extends PerformanceScript<String,CacheStore1> {
    CacheStoreWBPut2Script(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, CacheStore1>> cz) {
        super(new CacheStore1Configuration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("cachestore2 put："+enterParam.toString());
        CacheStoreWBPut2Script scirpt = new CacheStoreWBPut2Script(enterParam,CacheStoreWBPut2ScriptWork.class) ;
        scirpt.start();
    }
}
