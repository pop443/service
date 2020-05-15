package com.newland.boss.script.performance.cachestore;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.cachestore.CacheStore1;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/3/10.
 */
public class CacheStoreWBPut2ScriptWork extends PerformanceScriptWork<String, CacheStore1> {
    public CacheStoreWBPut2ScriptWork(EnterParam enterParam, IgniteCache<String, CacheStore1> igniteCache, IgniteDataStreamer<String, CacheStore1> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<CacheStore1> build = new CustObjBuild<>(CacheStore1.class) ;
        List<CacheStore1> list = new ArrayList<>() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = baseKey+i+"";
            CacheStore1 obj = build.build1k(randomKey) ;
            list.add(obj) ;

        }
        long l1 = System.currentTimeMillis() ;
        for (CacheStore1 obj:list) {
            igniteCache.put(obj.getId(),obj) ;
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}