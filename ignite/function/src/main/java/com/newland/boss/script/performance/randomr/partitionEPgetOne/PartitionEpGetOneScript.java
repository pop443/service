package com.newland.boss.script.performance.randomr.partitionEPgetOne;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomr.partitionEPget.PartitionEpGetScriptWork;

/**
 * 随机读性能测试 1K 大小分区缓存 put
 */
public class PartitionEpGetOneScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionEpGetOneScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>> cz) {
        super(new PartitionCustObjConfiguration(), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        //ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("EP get 单条："+enterParam.toString());
        PartitionEpGetOneScript scirpt = new PartitionEpGetOneScript(enterParam,PartitionEpGetScriptWork.class) ;
        scirpt.start();
    }

}
