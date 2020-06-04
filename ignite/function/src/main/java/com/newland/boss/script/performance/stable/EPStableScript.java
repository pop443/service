package com.newland.boss.script.performance.stable;

import com.newland.boss.entity.performance.cachestore.CacheStore1;
import com.newland.boss.entity.performance.cachestore.CacheStore1Configuration;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 稳定性
 */
public class EPStableScript extends PerformanceScript<String,CacheStore1> {
    EPStableScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, CacheStore1>> cz) {
        super(new CacheStore1Configuration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("EP 再平衡："+enterParam.toString());
        EPStableScript scirpt = new EPStableScript(enterParam,EPStableScriptWork.class) ;
        scirpt.start();
    }
}

