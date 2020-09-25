package com.newland.boss.script.performance.affinityindexkey.cache2.put20;

import com.newland.boss.entity.performance.affinitykey.Cache2CacheConfiguration;
import com.newland.boss.entity.performance.affinitykey.Cache2Key;
import com.newland.boss.entity.performance.affinitykey.Cache2Value;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 测试索引
 */
public class IndexPutScript extends PerformanceScript<Cache2Key,Cache2Value> {
    IndexPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<Cache2Key,Cache2Value>> cz) {
        super(new Cache2CacheConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("AffinityItemYes put："+enterParam.toString());
        IndexPutScript scirpt = new IndexPutScript(enterParam,IndexPutScriptWork.class) ;
        scirpt.start();
    }
}
