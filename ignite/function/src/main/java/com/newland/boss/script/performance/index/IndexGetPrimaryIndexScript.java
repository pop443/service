package com.newland.boss.script.performance.index;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.entity.performance.affinity.AffinityItemNoConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机读性能测试 4K 大对象Partition put
 */
public class IndexGetPrimaryIndexScript extends PerformanceScript<String,AffinityItemNo> {
    IndexGetPrimaryIndexScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, AffinityItemNo>> cz) {
        super(new AffinityItemNoConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("index getkey："+enterParam.toString());
        IndexGetPrimaryIndexScript scirpt = new IndexGetPrimaryIndexScript(enterParam,IndexGetPrimaryIndexScriptWork.class) ;
        scirpt.start();
    }

}
