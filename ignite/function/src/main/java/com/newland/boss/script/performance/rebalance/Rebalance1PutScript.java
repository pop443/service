package com.newland.boss.script.performance.rebalance;

import com.newland.boss.entity.performance.failover.PartitionLevel1;
import com.newland.boss.entity.performance.failover.PartitionLevel1Configuration;
import com.newland.boss.entity.performance.rebalance.Rebalance1;
import com.newland.boss.entity.performance.rebalance.Rebalance1Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class Rebalance1PutScript extends PerformanceScript<String,Rebalance1> {
    Rebalance1PutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, Rebalance1>> cz) {
        super(new Rebalance1Configuration(), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("同步再平衡模式 put："+enterParam.toString());
        Rebalance1PutScript scirpt = new Rebalance1PutScript(enterParam,Rebalance1PutScriptWork.class) ;
        scirpt.start();
    }
}
