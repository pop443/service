package com.newland.boss.script.performance.randomw.partitionbigstreamput;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class PartitionBigSteamPutScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionBigSteamPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>> cz) {
        super(new PartitionCustObjConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("大对象Partition stream："+enterParam.toString());
        PartitionBigSteamPutScript scirpt = new PartitionBigSteamPutScript(enterParam,PartitionBigSteamPutScriptWork.class) ;
        scirpt.start();
    }
}

