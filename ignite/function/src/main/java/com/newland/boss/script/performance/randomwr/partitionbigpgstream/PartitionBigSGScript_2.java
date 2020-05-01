package com.newland.boss.script.performance.randomwr.partitionbigpgstream;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomr.partitionbigget.PartitionBigGetScriptWork;
import com.newland.boss.script.performance.randomw.partitionbigstreamput.PartitionBigSteamPutScriptWork;

/**
 * Created by xz on 2020/3/14.
 */
public class PartitionBigSGScript_2 extends PerformanceScript<String,PartitionCustObj> {
    PartitionBigSGScript_2(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>>... cz) {
        super(new PartitionCustObjConfiguration(1), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("大对象Partition get&put stream 1主1备："+enterParam.toString());
        PartitionBigSGScript_2 scirpt = new PartitionBigSGScript_2(enterParam,PartitionBigSteamPutScriptWork.class, PartitionBigGetScriptWork.class) ;
        scirpt.start();
    }}
