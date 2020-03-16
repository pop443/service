package com.newland.boss.script.performance.bulk;

import com.newland.boss.entity.performance.bulk.JavaObj;
import com.newland.boss.entity.performance.bulk.JavaObjConfiguration;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class JavaPutStreamScript extends PerformanceScript<String,JavaObj> {
    JavaPutStreamScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, JavaObj>> cz) {
        super(new JavaObjConfiguration(), enterParam, cz);
    }

        @Override
        protected void afterInitIgnite() {
            ignite.destroyCache(cacheName);
        }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("java："+enterParam.toString());
        JavaPutStreamScript scirpt = new JavaPutStreamScript(enterParam,JavaPutStreamScriptWork.class) ;
        scirpt.start();
    }
}
