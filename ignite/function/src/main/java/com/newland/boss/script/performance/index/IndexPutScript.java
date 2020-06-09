package com.newland.boss.script.performance.index;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.entity.performance.affinity.AffinityItemNoConfiguration;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 测试索引
 */
public class IndexPutScript extends PerformanceScript<String,AffinityItemNo> {
    IndexPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, AffinityItemNo>> cz) {
        super(new AffinityItemNoConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("AffinityItemNo put："+enterParam.toString());
        IndexPutScript scirpt = new IndexPutScript(enterParam,IndexPutScriptWork.class) ;
        scirpt.start();
    }
}
