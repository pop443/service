package com.newland.boss.script.performance.randomr.partitionbigget;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机读性能测试 4K 大对象Partition put
 */
public class PartitionBigGetScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionBigGetScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>> cz) {
        super(new PartitionCustObjConfiguration(), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        //ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("大对象Partition get："+enterParam.toString());
        PartitionBigGetScript scirpt = new PartitionBigGetScript(enterParam,PartitionBigGetScriptWork.class) ;
        scirpt.start();
    }

}
