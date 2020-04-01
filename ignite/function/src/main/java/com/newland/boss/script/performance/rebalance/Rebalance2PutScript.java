package com.newland.boss.script.performance.rebalance;

import com.newland.boss.entity.performance.rebalance.Rebalance2;
import com.newland.boss.entity.performance.rebalance.Rebalance2Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
    public class Rebalance2PutScript extends PerformanceScript<String,Rebalance2> {
    Rebalance2PutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, Rebalance2>> cz) {
        super(new Rebalance2Configuration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("异步再平衡模式 put："+enterParam.toString());
        Rebalance2PutScript scirpt = new Rebalance2PutScript(enterParam,Rebalance2PutScriptWork.class) ;
        scirpt.start();
    }
}
