package com.newland.boss.script.performance.rack.rackaware;

import com.newland.boss.entity.performance.rack.MacCustObj;
import com.newland.boss.entity.performance.rack.MacCustObjConfiguration;
import com.newland.boss.entity.performance.rack.Rack1CustObj;
import com.newland.boss.entity.performance.rack.Rack1CustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机读性能测试 4K 大对象Partition put
 */
public class Rack1GetScript extends PerformanceScript<String,Rack1CustObj> {
    Rack1GetScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, Rack1CustObj>> cz) {
        super(new Rack1CustObjConfiguration(), enterParam, cz);
    }
    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Partition get："+enterParam.toString());
        Rack1GetScript scirpt = new Rack1GetScript(enterParam,Rack1GetScriptWork.class) ;
        scirpt.start();
    }
}
