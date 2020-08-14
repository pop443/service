package com.newland.boss.script.performance.bulk.binary;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.bulk.JavaObj;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.IgniteCache;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class BytePutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,byte[]> igniteCache ;
    private Integer baseKey;
    public BytePutScriptWork(EnterParam enterParam, IgniteCache<String,byte[]> igniteCache, Integer baseKey) {
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
        Map<String,byte[]> map = new HashMap<>() ;
        CustObjBuild<JavaObj> build1 = new CustObjBuild<>(JavaObj.class) ;
        System.out.println("数据构造 start");
        try {
            for (int i = 0; i < enterParam.getCount(); i++) {
                String randomKey = i+baseKey+"" ;
                JavaObj obj = build1.build1k(randomKey) ;
                map.put(obj.getId(),objectMapper.writeValueAsBytes(obj)) ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        Iterator<Map.Entry<String,byte[]>> it = map.entrySet().iterator() ;
        while (it.hasNext()){
            Map.Entry<String,byte[]> entry = it.next() ;
            igniteCache.put(entry.getKey(),entry.getValue());
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }
}
