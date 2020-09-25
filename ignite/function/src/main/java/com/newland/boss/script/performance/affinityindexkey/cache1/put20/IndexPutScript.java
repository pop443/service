package com.newland.boss.script.performance.affinityindexkey.cache1.put20;

import com.newland.boss.entity.performance.affinitykey.Cache1CacheConfiguration;
import com.newland.boss.entity.performance.affinitykey.Cache1Key;
import com.newland.boss.entity.performance.affinitykey.Cache1Value;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 测试索引
 */
public class IndexPutScript extends PerformanceScript<Cache1Key,Cache1Value> {
    IndexPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<Cache1Key,Cache1Value>> cz) {
        super(new Cache1CacheConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("AffinityItemYes put："+enterParam.toString());
        IndexPutScript scirpt = new IndexPutScript(enterParam,IndexPutScriptWork.class) ;
        scirpt.start();
    }
}
