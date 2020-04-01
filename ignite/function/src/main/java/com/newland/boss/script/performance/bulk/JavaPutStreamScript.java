package com.newland.boss.script.performance.bulk;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.bulk.ByteObjConfiguration;
import com.newland.boss.entity.performance.bulk.JavaObj;
import com.newland.boss.entity.performance.bulk.JavaObjConfiguration;
import com.newland.boss.entity.performance.bulk.JsonObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
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
public class JavaPutStreamScript {
    private Ignite ignite;
    private IgniteCache<String, JavaObj> igniteCache;
    private EnterParam enterParam;
    private Random random;

    public JavaPutStreamScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
        JavaObjConfiguration jsonObjConfiguration = new JavaObjConfiguration();
        CacheConfiguration<String, JavaObj> cacheConfiguration = jsonObjConfiguration.getCacheConfiguration();
        igniteCache = ignite.getOrCreateCache(cacheConfiguration);
        this.enterParam = enterParam;
        this.random = new Random();

    }
    public void start() {
        long l1 = System.currentTimeMillis();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, JavaObj> map = new HashMap<>();
            CustObjBuild<JavaObj> build = new CustObjBuild<>(JavaObj.class);
            for (int i = 0; i < enterParam.getCount(); i++) {
                String randomKey = random.nextInt(enterParam.getCount()) + enterParam.getCount() + "";
                JavaObj obj = build.build1k(randomKey + "");
                map.put(obj.getId(), obj);
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
        System.out.println("java：" + enterParam.toString());
        JavaPutStreamScript scirpt = new JavaPutStreamScript(enterParam);
        scirpt.start();
    }
}
