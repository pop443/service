package com.newland.boss.script.performance.valuefilter;

import com.newland.boss.entity.performance.affinity.AffinityItemYes;
import com.newland.boss.entity.performance.affinity.AffinityItemYesConfiguration;
import com.newland.boss.entity.performance.affinity.AffinityItemYesKey;
import com.newland.boss.entity.performance.complex.ListObj;
import com.newland.boss.entity.performance.complex.ListObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机读性能测试 4K 大对象Partition put
 */
public class ValueFilterGetScript extends PerformanceScript<String,ListObj> {
    ValueFilterGetScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String,ListObj>> cz) {
        super(new ListObjConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("index getkey："+enterParam.toString());
        ValueFilterGetScript scirpt = new ValueFilterGetScript(enterParam,ValueFilterGetScriptWork.class) ;
        scirpt.start();
    }

}
