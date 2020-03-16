package com.newland.boss.script.performance.bulk;

import com.newland.boss.entity.performance.bulk.ByteObjConfiguration;
import com.newland.boss.entity.performance.bulk.JsonObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class BytePutStreamScript extends PerformanceScript<String, byte[]> {
    BytePutStreamScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, byte[]>> cz) {
        super(new ByteObjConfiguration(), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("java：" + enterParam.toString());
        BytePutStreamScript scirpt = new BytePutStreamScript(enterParam, BytePutStreamScriptWork.class);
        scirpt.start();
    }
}
