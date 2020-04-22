package com.newland.boss.script.performance.complex;

import com.newland.boss.entity.performance.complex.ComplexConfiguration;
import com.newland.boss.entity.performance.complex.ComplexKey;
import com.newland.boss.entity.performance.complex.ComplexValue;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class ComplexStreamPutScript extends PerformanceScript<ComplexKey,ComplexValue> {
    ComplexStreamPutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<ComplexKey, ComplexValue>> cz) {
        super(new ComplexConfiguration(), enterParam, cz);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        ComplexStreamPutScript scirpt = new ComplexStreamPutScript(enterParam,ComplexStreamPutScriptWork.class);
        scirpt.start();
    }
}
