package com.newland.boss.script.performance.rangecalc;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.entity.performance.affinity.AffinityItemNoConfiguration;
import com.newland.boss.entity.performance.affinity.AffinityMain;
import com.newland.boss.entity.performance.affinity.AffinityMainConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 多表多次put
 */
public class RangeCalcIndexScript extends PerformanceScript<String,AffinityItemNo> {
    RangeCalcIndexScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, AffinityItemNo>> cz) {
        super(new AffinityItemNoConfiguration(), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        //ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("范围索引：" + enterParam.toString());
        RangeCalcIndexScript scirpt = new RangeCalcIndexScript(enterParam,RangeCalcIndexScriptWork.class);
        scirpt.start();
    }

}