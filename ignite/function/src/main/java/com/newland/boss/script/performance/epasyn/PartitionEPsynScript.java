package com.newland.boss.script.performance.epasyn;

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
public class PartitionEPsynScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionEPsynScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>>... cz) {
        super(new PartitionCustObjConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("EP get&put(多笔数据) 同步："+enterParam.toString());
        PartitionEPsynScript scirpt = new PartitionEPsynScript(enterParam,PartitionSmallEpPutScriptWork.class, PartitionEpGetScriptWork.class) ;
        scirpt.start();
    }}
