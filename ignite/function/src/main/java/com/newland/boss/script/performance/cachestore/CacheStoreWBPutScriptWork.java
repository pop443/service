package com.newland.boss.script.performance.cachestore;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.cachestore.CacheStore2;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.ignite.label.utils.IdGen;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.*;

/**
 * Created by xz on 2020/3/10.
 */
public class CacheStoreWBPutScriptWork extends PerformanceScriptWork<String, CacheStore2> {
    public CacheStoreWBPutScriptWork(EnterParam enterParam, IgniteCache<String, CacheStore2> igniteCache, IgniteDataStreamer<String, CacheStore2> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<CacheStore2> build = new CustObjBuild<>(CacheStore2.class) ;
        List<CacheStore2> list = new ArrayList<>() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = baseKey+i+"";
            CacheStore2 obj = build.build1k(randomKey) ;
            list.add(obj) ;

        }
        long l1 = System.currentTimeMillis() ;
        for (CacheStore2 obj:list) {
            igniteCache.put(obj.getId(),obj) ;
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}