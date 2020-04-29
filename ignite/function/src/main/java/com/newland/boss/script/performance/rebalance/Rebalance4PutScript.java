package com.newland.boss.script.performance.rebalance;

import com.newland.boss.entity.performance.rebalance.Rebalance4;
import com.newland.boss.entity.performance.rebalance.Rebalance4Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class Rebalance4PutScript extends PerformanceScript<String,Rebalance4> {
    Rebalance4PutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, Rebalance4>> cz) {
        super(new Rebalance4Configuration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("分区再平衡模式 put："+enterParam.toString());
        Rebalance4PutScript scirpt = new Rebalance4PutScript(enterParam,Rebalance4PutScriptWork.class) ;
        scirpt.start();
    }
}
