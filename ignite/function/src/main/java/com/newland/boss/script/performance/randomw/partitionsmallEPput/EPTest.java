package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.cachestore.entity.ExpiryConfiguration;

/**
 * Created by xz on 2020/4/30.
 */
public class EPTest extends PerformanceScript<String, Expiry> {
    EPTest(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, Expiry>> cz) {
        super(new ExpiryConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        enterParam.setThreadNum(1);
        enterParam.setLoop(1);
        System.out.println("EPtest：" + enterParam.toString());
        EPTest scirpt = new EPTest(enterParam, EPTestWork.class);
        scirpt.start();
    }
}
