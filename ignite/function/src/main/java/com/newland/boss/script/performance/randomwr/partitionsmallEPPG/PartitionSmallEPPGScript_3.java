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
public class PartitionSmallEPPGScript_3 extends PerformanceScript<String,PartitionCustObj> {
    PartitionSmallEPPGScript_3(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>>... cz) {
        super(new PartitionCustObjConfiguration(2), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("EP get&put(多笔数据) 1主2备："+enterParam.toString());
        PartitionSmallEPPGScript_3 scirpt = new PartitionSmallEPPGScript_3(enterParam,PartitionSmallEpPutGetScriptWork.class) ;
        scirpt.start();
    }}
