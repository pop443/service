package com.newland.boss.script.performance.randomwr.partitionbigpg;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomr.partitionbigget.PartitionBigGetScriptWork;
import com.newland.boss.script.performance.randomw.partitionbigput.PartitionBigPutScriptWork;

/**
 * Created by xz on 2020/3/14.
 */
public class PartitionBigPGScript_3 extends PerformanceScript<String,PartitionCustObj> {
    PartitionBigPGScript_3(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>>... cz) {
        super(new PartitionCustObjConfiguration(2), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("大对象Partition get&put 1主2备："+enterParam.toString());
        PartitionBigPGScript_3 scirpt = new PartitionBigPGScript_3(enterParam,PartitionBigPutGetScriptWork.class) ;
        scirpt.start();
    }}
