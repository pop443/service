package com.newland.boss.script.performance.transcation.simple;

import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache1Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class Transcation1EpPutScript extends PerformanceScript<String,TranscationCache1> {
    Transcation1EpPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, TranscationCache1>> cz) {
        super(new TranscationCache1Configuration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Transcation1Put put："+enterParam.toString());
        Transcation1EpPutScript scirpt = new Transcation1EpPutScript(enterParam,Transcation1EpPutScriptWork.class) ;
        scirpt.start();
    }
}
