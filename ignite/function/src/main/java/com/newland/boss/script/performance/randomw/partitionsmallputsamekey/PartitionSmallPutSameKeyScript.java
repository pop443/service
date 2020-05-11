package com.newland.boss.script.performance.randomw.partitionsmallputsamekey;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class PartitionSmallPutSameKeyScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionSmallPutSameKeyScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>> cz) {
        super(new PartitionCustObjConfiguration(0), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Partition put 单 key："+enterParam.toString());
        PartitionSmallPutSameKeyScript scirpt = new PartitionSmallPutSameKeyScript(enterParam,PartitionSmallPutSameKeyScriptWork.class) ;
        scirpt.start();
    }
}
