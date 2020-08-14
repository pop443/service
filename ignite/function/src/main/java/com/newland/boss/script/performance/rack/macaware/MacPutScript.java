package com.newland.boss.script.performance.rack.macaware;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.entity.performance.rack.MacCustObj;
import com.newland.boss.entity.performance.rack.MacCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class MacPutScript extends PerformanceScript<String,MacCustObj> {
    MacPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, MacCustObj>> cz) {
        super(new MacCustObjConfiguration(2), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("MacCustObj put："+enterParam.toString());
        MacPutScript scirpt = new MacPutScript(enterParam,MacPutScriptWork.class) ;
        scirpt.start();
    }
}
