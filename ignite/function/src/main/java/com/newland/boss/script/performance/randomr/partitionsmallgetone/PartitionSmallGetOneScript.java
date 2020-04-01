package com.newland.boss.script.performance.randomr.partitionsmallgetone;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机读性能测试 4K 大对象Partition put
 */
public class PartitionSmallGetOneScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionSmallGetOneScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>> cz) {
        super(new PartitionCustObjConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Partition get："+enterParam.toString());
        PartitionSmallGetOneScript scirpt = new PartitionSmallGetOneScript(enterParam,PartitionSmallGetOneScriptWork.class) ;
        scirpt.start();
    }

}
