package com.newland.boss.script.performance.rack.rackaware;

import com.newland.boss.entity.performance.rack.Rack2CustObj;
import com.newland.boss.entity.performance.rack.Rack2CustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机读性能测试 4K 大对象Partition put
 */
public class Rack2GetScript extends PerformanceScript<String,Rack2CustObj> {
    Rack2GetScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, Rack2CustObj>> cz) {
        super(new Rack2CustObjConfiguration(), enterParam, cz);
    }
    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Partition get："+enterParam.toString());
        Rack2GetScript scirpt = new Rack2GetScript(enterParam,Rack2GetScriptWork.class) ;
        scirpt.start();
    }
}
