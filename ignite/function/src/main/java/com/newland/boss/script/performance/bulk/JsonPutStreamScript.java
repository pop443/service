package com.newland.boss.script.performance.bulk;

import com.newland.boss.entity.performance.bulk.JavaObj;
import com.newland.boss.entity.performance.bulk.JavaObjConfiguration;
import com.newland.boss.entity.performance.bulk.JsonObj;
import com.newland.boss.entity.performance.bulk.JsonObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class JsonPutStreamScript extends PerformanceScript<String,String> {
    JsonPutStreamScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, String>> cz) {
        super(new JsonObjConfiguration(), enterParam, cz);
    }

        @Override
        protected void afterInitIgnite() {
            ignite.destroyCache(cacheName);
        }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("json："+enterParam.toString());
        JsonPutStreamScript scirpt = new JsonPutStreamScript(enterParam,JsonPutStreamScriptWork.class) ;
        scirpt.start();
    }
}
