package com.newland.boss.script.performance.stream;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * Created by xz on 2020/4/30.
 */
public class PartitionStreamPutScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionStreamPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>> cz) {
        super(new PartitionCustObjConfiguration(1), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Partition put："+enterParam.toString());
        PartitionStreamPutScript scirpt = new PartitionStreamPutScript(enterParam,PartitionStreamPutScriptWork.class) ;
        scirpt.start();
    }
}