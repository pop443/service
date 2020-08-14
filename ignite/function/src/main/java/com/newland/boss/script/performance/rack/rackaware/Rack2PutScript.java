package com.newland.boss.script.performance.rack.rackaware;

import com.newland.boss.entity.performance.rack.Rack1CustObj;
import com.newland.boss.entity.performance.rack.Rack1CustObjConfiguration;
import com.newland.boss.entity.performance.rack.Rack2CustObj;
import com.newland.boss.entity.performance.rack.Rack2CustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class Rack2PutScript extends PerformanceScript<String,Rack2CustObj> {
    Rack2PutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, Rack2CustObj>> cz) {
        super(new Rack2CustObjConfiguration(2), enterParam, cz);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Rack2CustObj simple put："+enterParam.toString());
        Rack2PutScript scirpt = new Rack2PutScript(enterParam,Rack2PutScriptWork.class) ;
        scirpt.start();
    }
}
