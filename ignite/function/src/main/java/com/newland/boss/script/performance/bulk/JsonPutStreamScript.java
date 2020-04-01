package com.newland.boss.script.performance.bulk;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.bulk.*;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class JsonPutStreamScript {
    private Ignite ignite;
    private IgniteCache<String, String> igniteCache;
    private EnterParam enterParam;
    private Random random;

    public JsonPutStreamScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
        JsonObjConfiguration jsonObjConfiguration = new JsonObjConfiguration();
        CacheConfiguration<String, String> cacheConfiguration = jsonObjConfiguration.getCacheConfiguration();
        igniteCache = ignite.getOrCreateCache(cacheConfiguration);
        this.enterParam = enterParam;
        this.random = new Random();

    }

    public void start() {
        long l1 = System.currentTimeMillis();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = new HashMap<>();
            CustObjBuild<JsonObj> build = new CustObjBuild<>(JsonObj.class);
            for (int i = 0; i < enterParam.getCount(); i++) {
                String randomKey = random.nextInt(enterParam.getCount()) + enterParam.getCount() + "";
                JsonObj obj = build.build1k(randomKey + "");
                String value = objectMapper.writeValueAsString(obj);
                map.put(obj.getId(), value);
            }
            igniteCache.putAll(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("总消耗" + (System.currentTimeMillis() - l1));
        destory();
    }

    protected void destory() {
        igniteCache.close();
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("json：" + enterParam.toString());
        JsonPutStreamScript scirpt = new JsonPutStreamScript(enterParam);
        scirpt.start();
    }
}
