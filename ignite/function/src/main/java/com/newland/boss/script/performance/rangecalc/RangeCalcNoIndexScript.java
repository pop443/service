package com.newland.boss.script.performance.rangecalc;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.entity.performance.affinity.AffinityItemNoConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 多表多次put
 */
public class RangeCalcNoIndexScript extends PerformanceScript<String,AffinityItemNo> {
    RangeCalcNoIndexScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, AffinityItemNo>> cz) {
        super(new AffinityItemNoConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("范围非索引：" + enterParam.toString());
        RangeCalcNoIndexScript scirpt = new RangeCalcNoIndexScript(enterParam,RangeCalcNoIndexScriptWork.class);
        scirpt.start();
    }

}
