package com.newland.boss.script.performance.rack.macaware;

import com.newland.boss.entity.performance.rack.MacCustObj;
import com.newland.boss.entity.performance.rack.MacCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机读性能测试 4K 大对象Partition put
 */
public class MacGetScript extends PerformanceScript<String,MacCustObj> {
    MacGetScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, MacCustObj>> cz) {
        super(new MacCustObjConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Partition get："+enterParam.toString());
        MacGetScript scirpt = new MacGetScript(enterParam,MacGetScriptWork.class) ;
        scirpt.start();
    }

}
