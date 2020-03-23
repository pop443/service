package com.newland.boss.script.features.consistency.replicated;

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
public class ReplicatedSYNCPGScript extends PerformanceScript<String,PartitionCustObj> {
    ReplicatedSYNCPGScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>>... cz) {
        super(new PartitionCustObjConfiguration(CacheRebalanceMode.SYNC), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("全复制 同步再平衡："+enterParam.toString());
        ReplicatedSYNCPGScript scirpt = new ReplicatedSYNCPGScript(enterParam,PartitionSmallPutScriptWork.class, PartitionBigGetScriptWork.class) ;
        scirpt.start();
    }}
