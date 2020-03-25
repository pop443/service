package com.newland.boss.script.features.export;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.entity.resource.FreeResource;
import com.newland.boss.entity.resource.FreeResourceConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 导入数据
 */
public class ExportPutScript extends PerformanceScript<String,FreeResource> {
    ExportPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, FreeResource>> cz) {
        super(new FreeResourceConfiguration(), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("FreeResource put："+enterParam.toString());
        ExportPutScript scirpt = new ExportPutScript(enterParam,ExportPutScriptWork.class) ;
        scirpt.start();
    }
}
