package com.newland.boss.script.performance.randomw.partitionbigput;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 4K 大对象Partition put
 */
public class PartitionBigPutScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionBigPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>> cz) {
        super(new PartitionCustObjConfiguration(), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("大对象Partition put："+enterParam.toString());
        PartitionBigPutScript scirpt = new PartitionBigPutScript(enterParam,PartitionBigPutScriptWork.class) ;
        scirpt.start();
    }
}

