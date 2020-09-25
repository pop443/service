package com.newland.boss.script.performance.indexkey;

import com.newland.boss.entity.performance.affinity.*;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 测试索引
 */
public class IndexPutScript extends PerformanceScript<AffinityItemYesKey,AffinityItemYes> {
    IndexPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<AffinityItemYesKey,AffinityItemYes>> cz) {
        super(new AffinityItemYesConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("AffinityItemYes put："+enterParam.toString());
        IndexPutScript scirpt = new IndexPutScript(enterParam,IndexPutScriptWork.class) ;
        scirpt.start();
    }
}
