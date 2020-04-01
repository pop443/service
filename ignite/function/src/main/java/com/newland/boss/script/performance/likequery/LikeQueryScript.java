package com.newland.boss.script.performance.likequery;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.entity.performance.affinity.AffinityItemNoConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 多表多次put
 */
public class LikeQueryScript extends PerformanceScript<String,AffinityItemNo> {
    LikeQueryScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, AffinityItemNo>> cz) {
        super(new AffinityItemNoConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("范围索引：" + enterParam.toString());
        LikeQueryScript scirpt = new LikeQueryScript(enterParam,LikeQueryScriptWork.class);
        scirpt.start();
    }

}
