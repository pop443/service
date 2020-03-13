package com.newland.boss.script.performance.randomwr.partitionsmallpgstream;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomr.partitionbigget.PartitionBigGetScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallstreamput.PartitionSmallSteamPutScriptWork;

/**
 * Created by xz on 2020/3/14.
 */
public class PartitionSmallSGScript_2 extends PerformanceScript<String,PartitionCustObj> {
    PartitionSmallSGScript_2(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>>... cz) {
        super(new PartitionCustObjConfiguration(1), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Partition get&put stream 1主1备："+enterParam.toString());
        PartitionSmallSGScript_2 scirpt = new PartitionSmallSGScript_2(enterParam,PartitionSmallSteamPutScriptWork.class, PartitionBigGetScriptWork.class) ;
        scirpt.start();
    }}
