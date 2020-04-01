package com.newland.boss.script.performance.randomwr.partitionsmallEPPG;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomr.partitionEPget.PartitionEpGetScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PartitionSmallEpPutScriptWork;

/**
 * Created by xz on 2020/3/14.
 */
public class PartitionSmallEPPGScript_2 extends PerformanceScript<String,PartitionCustObj> {
    PartitionSmallEPPGScript_2(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>>... cz) {
        super(new PartitionCustObjConfiguration(1), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("EP get&put(多笔数据) 1主1备："+enterParam.toString());
        PartitionSmallEPPGScript_2 scirpt = new PartitionSmallEPPGScript_2(enterParam,PartitionSmallEpPutScriptWork.class, PartitionEpGetScriptWork.class) ;
        scirpt.start();
    }}
