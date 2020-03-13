package com.newland.boss.script.performance.randomr.partitionEPget;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.utils.Threads;

import java.util.concurrent.*;

/**
 * 随机读性能测试 1K 大小分区缓存 put
 */
public class PartitionEpGetScript extends PerformanceScript<String,PartitionCustObj> {
    PartitionEpGetScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionCustObj>> cz) {
        super(new PartitionCustObjConfiguration(), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        //ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("EP get(多笔数据)："+enterParam.toString());
        PartitionEpGetScript scirpt = new PartitionEpGetScript(enterParam,PartitionEpGetScriptWork.class) ;
        scirpt.start();
    }

}
