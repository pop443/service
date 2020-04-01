package com.newland.boss.script.performance.randomr.nearsmallget;

import com.newland.boss.entity.performance.obj.NearSmallCustObj;
import com.newland.boss.entity.performance.obj.NearSmallCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机读性能测试 1K 大小分区缓存 put
 */
public class NearSmallGetScript extends PerformanceScript<String,NearSmallCustObj>{
    NearSmallGetScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, NearSmallCustObj>> cz) {
        super(new NearSmallCustObjConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Near get："+enterParam.toString());
        NearSmallGetScript scirpt = new NearSmallGetScript(enterParam,NearSmallGetScriptWork.class) ;
        scirpt.start();
    }


}
