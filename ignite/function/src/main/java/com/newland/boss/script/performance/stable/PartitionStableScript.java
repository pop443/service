package com.newland.boss.script.performance.stable;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 稳定性
 */
public class PartitionStableScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionStableScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>> cz) {
        super(new PartitionCustObjConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("稳定性8小时："+enterParam.toString());
        PartitionStableScript scirpt = new PartitionStableScript(enterParam,PartitionStableScriptWork.class) ;
        scirpt.start();
    }
}

