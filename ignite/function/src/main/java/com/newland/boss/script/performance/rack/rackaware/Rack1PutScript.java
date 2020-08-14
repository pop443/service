package com.newland.boss.script.performance.rack.rackaware;

import com.newland.boss.entity.performance.rack.Rack1CustObj;
import com.newland.boss.entity.performance.rack.Rack1CustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class Rack1PutScript extends PerformanceScript<String,Rack1CustObj> {
    Rack1PutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, Rack1CustObj>> cz) {
        super(new Rack1CustObjConfiguration(2), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Rack1CustObj not simple put："+enterParam.toString());
        Rack1PutScript scirpt = new Rack1PutScript(enterParam,Rack1PutScriptWork.class) ;
        scirpt.start();
    }
}
