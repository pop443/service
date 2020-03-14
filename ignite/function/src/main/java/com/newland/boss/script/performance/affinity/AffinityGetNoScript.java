package com.newland.boss.script.performance.affinity;

import com.newland.boss.entity.performance.affinity.AffinityMain;
import com.newland.boss.entity.performance.affinity.AffinityMainConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 多表多次put
 */
public class AffinityGetNoScript extends PerformanceScript<String,AffinityMain> {
    AffinityGetNoScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, AffinityMain>> cz) {
        super(new AffinityMainConfiguration(), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        //ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("affinity 非并置查找：" + enterParam.toString());
        AffinityGetNoScript scirpt = new AffinityGetNoScript(enterParam,AffinityGetNoScriptWork.class);
        scirpt.start();
    }

}
