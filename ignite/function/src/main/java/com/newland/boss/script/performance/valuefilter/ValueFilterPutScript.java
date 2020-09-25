package com.newland.boss.script.performance.valuefilter;

import com.newland.boss.entity.performance.complex.ListObj;
import com.newland.boss.entity.performance.complex.ListObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 测试索引
 */
public class ValueFilterPutScript extends PerformanceScript<String,ListObj> {
    ValueFilterPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String,ListObj>> cz) {
        super(new ListObjConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("ListObj put："+enterParam.toString());
        ValueFilterPutScript scirpt = new ValueFilterPutScript(enterParam,ValueFilterPutScriptWork.class) ;
        scirpt.start();
    }
}
