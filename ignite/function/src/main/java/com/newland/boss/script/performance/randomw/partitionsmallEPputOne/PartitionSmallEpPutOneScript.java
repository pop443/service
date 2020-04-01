package com.newland.boss.script.performance.randomw.partitionsmallEPputOne;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PartitionSmallEpPutScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class PartitionSmallEpPutOneScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionSmallEpPutOneScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>> cz) {
        super(new PartitionCustObjConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("EP put(多笔数据)："+enterParam.toString());
        PartitionSmallEpPutOneScript scirpt = new PartitionSmallEpPutOneScript(enterParam,PartitionSmallEpPutOneScriptAsynWork.class) ;
        scirpt.start();
    }
}