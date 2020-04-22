package com.newland.boss.script.performance.complex;

import com.newland.boss.entity.performance.complex.*;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class SimpleStreamPutScript extends PerformanceScript<String,SimpleValue> {
    SimpleStreamPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String,SimpleValue>> cz) {
        super(new SimpleConfiguration(), enterParam, cz);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        SimpleStreamPutScript scirpt = new SimpleStreamPutScript(enterParam,SimpleStreamPutScriptWork.class);
        scirpt.start();
    }
}
