package com.newland.boss.script.performance.bulk.json;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.bulk.JavaObj;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.IgniteCache;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class JsonPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,String> igniteCache ;
    private Integer baseKey;
    public JsonPutScriptWork(EnterParam enterParam, IgniteCache<String,String> igniteCache, Integer baseKey) {
        this.enterParam = enterParam;
        this.igniteCache = igniteCache ;
        this.baseKey = baseKey ;
    }

    @Override
    public Long call() throws Exception {
        return working();
    }

    private Long working() {
        ObjectMapper objectMapper = new ObjectMapper() ;
        Map<String,String> map = new HashMap<>() ;
        CustObjBuild<JavaObj> build1 = new CustObjBuild<>(JavaObj.class) ;
        System.out.println("数据构造 start");
        try {
            for (int i = 0; i < enterParam.getCount(); i++) {
                String randomKey = i+baseKey+"" ;
                JavaObj obj = build1.build1k(randomKey) ;
                map.put(obj.getId(),objectMapper.writeValueAsString(obj)) ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        Iterator<Map.Entry<String,String>> it = map.entrySet().iterator() ;
        while (it.hasNext()){
            Map.Entry<String,String> entry = it.next() ;
            igniteCache.put(entry.getKey(),entry.getValue());
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }
}
