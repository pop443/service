package com.newland.boss.script.performance.rebalance;

import com.newland.boss.entity.performance.rebalance.Rebalance3;
import com.newland.boss.entity.performance.rebalance.Rebalance3Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class Rebalance3PutScript extends PerformanceScript<String,Rebalance3> {
    Rebalance3PutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, Rebalance3>> cz) {
        super(new Rebalance3Configuration(), enterParam, cz);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("分区同步再平衡模式 put："+enterParam.toString());
        Rebalance3PutScript scirpt = new Rebalance3PutScript(enterParam,Rebalance3PutScriptWork.class) ;
        scirpt.start();
    }
}
