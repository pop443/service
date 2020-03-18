package com.newland.boss.script.performance.randomw.nearsmallput;

import com.newland.boss.entity.performance.obj.NearSmallCustObj;
import com.newland.boss.entity.performance.obj.NearSmallCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class NearSmallPutScript extends PerformanceScript<String,NearSmallCustObj> {
    NearSmallPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, NearSmallCustObj>> cz) {
        super(new NearSmallCustObjConfiguration(), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Near put："+enterParam.toString());
        NearSmallPutScript scirpt = new NearSmallPutScript(enterParam, NearSmallPutScriptWork.class) ;
        scirpt.start();
    }
}
