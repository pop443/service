package com.newland.boss.script.performance.randomwr.partitionsmallpgsamekey;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomr.partitionbigget.PartitionBigGetScriptWork;
import com.newland.boss.script.performance.randomr.partitionsmallgetsamekey.PartitionSmallGetSameKeyScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallput.PartitionSmallPutScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallputsamekey.PartitionSmallPutSameKeyScriptWork;

/**
 * Created by xz on 2020/3/14.
 */
public class PartitionSmallPGSameKeyScript_2 extends PerformanceScript<String,PartitionCustObj> {
    PartitionSmallPGSameKeyScript_2(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>>... cz) {
        super(new PartitionCustObjConfiguration(1), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Partition多表多次get&put 1主1备："+enterParam.toString());
        PartitionSmallPGSameKeyScript_2 scirpt = new PartitionSmallPGSameKeyScript_2(enterParam,PartitionSmallPutSameKeyScriptWork.class, PartitionSmallGetSameKeyScriptWork.class) ;
        scirpt.start();
    }}
