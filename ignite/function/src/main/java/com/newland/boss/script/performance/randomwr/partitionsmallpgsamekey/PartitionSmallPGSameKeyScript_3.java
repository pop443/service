package com.newland.boss.script.performance.randomwr.partitionsmallpgsamekey;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomr.partitionsmallgetsamekey.PartitionSmallGetSameKeyScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallputsamekey.PartitionSmallPutSameKeyScriptWork;

/**
 * Created by xz on 2020/3/14.
 */
public class PartitionSmallPGSameKeyScript_3 extends PerformanceScript<String,PartitionCustObj> {
    PartitionSmallPGSameKeyScript_3(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>>... cz) {
        super(new PartitionCustObjConfiguration(2), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        /*ignite.destroyCache(cacheName);*/
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Partition多表多次get&put 1主2备："+enterParam.toString());
        PartitionSmallPGSameKeyScript_3 scirpt = new PartitionSmallPGSameKeyScript_3(enterParam,PartitionSmallPutSameKeyScriptWork.class, PartitionSmallGetSameKeyScriptWork.class) ;
        scirpt.start();
    }}
