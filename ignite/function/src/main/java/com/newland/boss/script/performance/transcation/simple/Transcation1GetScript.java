package com.newland.boss.script.performance.transcation.simple;

import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache1Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机读性能测试 4K 大对象Partition put
 */
public class Transcation1GetScript extends PerformanceScript<String,TranscationCache1> {
    Transcation1GetScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, TranscationCache1>> cz) {
        super(new TranscationCache1Configuration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Partition get："+enterParam.toString());
        Transcation1GetScript scirpt = new Transcation1GetScript(enterParam,Transcation1GetScriptWork.class) ;
        scirpt.start();
    }

}
