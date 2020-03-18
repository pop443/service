package com.newland.boss.script.performance.rebalance;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomr.partitionbigget.PartitionBigGetScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallput.PartitionSmallPutScriptWork;
import org.apache.ignite.cache.CacheRebalanceMode;

/**
 * Created by xz on 2020/3/14.
 */
public class PartitionASYNCPGScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionASYNCPGScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>>... cz) {
        super(new PartitionCustObjConfiguration(CacheRebalanceMode.ASYNC), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Partition 异步再平衡："+enterParam.toString());
        PartitionASYNCPGScript scirpt = new PartitionASYNCPGScript(enterParam,PartitionSmallPutScriptWork.class, PartitionBigGetScriptWork.class) ;
        scirpt.start();
    }}
