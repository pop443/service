package com.newland.boss.script.performance.bulk;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.bulk.ByteObj;
import com.newland.boss.entity.performance.bulk.ByteObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
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
public class BytePutStreamScript {
    private Ignite ignite;
    private IgniteCache<String, byte[]> igniteCache;
    private EnterParam enterParam;
    private Random random;

    public BytePutStreamScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
        ByteObjConfiguration jsonObjConfiguration = new ByteObjConfiguration();
        CacheConfiguration<String, byte[]> cacheConfiguration = jsonObjConfiguration.getCacheConfiguration();
        igniteCache = ignite.getOrCreateCache(cacheConfiguration);
        this.enterParam = enterParam;
        this.random = new Random();

    }

    public void start() {
        long l1 = System.currentTimeMillis() ;
        try {
            ObjectMapper objectMapper = new ObjectMapper() ;
            Map<String,byte[]> map = new HashMap<>() ;
            CustObjBuild<ByteObj> build = new CustObjBuild<>(ByteObj.class) ;
            for (int i = 0; i < enterParam.getCount(); i++) {
                String randomKey = random.nextInt(enterParam.getCount()) + enterParam.getCount() + "";
                ByteObj obj = build.build1k(randomKey+"") ;
                byte[] bytes = objectMapper.writeValueAsBytes(obj);
                map.put(obj.getId(),bytes) ;
            }
            igniteCache.putAll(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("总消耗"+(System.currentTimeMillis()-l1));
        destory();
    }

    protected void destory() {
        igniteCache.close();
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("byte：" + enterParam.toString());
        BytePutStreamScript scirpt = new BytePutStreamScript(enterParam);
        scirpt.start();
    }
}
