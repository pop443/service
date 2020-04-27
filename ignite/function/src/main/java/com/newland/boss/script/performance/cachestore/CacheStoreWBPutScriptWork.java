package com.newland.boss.script.performance.cachestore;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.cachestore.CacheStore2;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.ignite.label.utils.IdGen;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by xz on 2020/3/10.
 */
public class CacheStoreWBPutScriptWork extends PerformanceScriptWork<String, CacheStore2> {
    public CacheStoreWBPutScriptWork(EnterParam enterParam, IgniteCache<String, CacheStore2> igniteCache, IgniteDataStreamer<String, CacheStore2> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public long doing() {
        long cost = 0 ;
        Map<String,CacheStore2> map = new HashMap<>() ;
        CustObjBuild<CacheStore2> build = new CustObjBuild<>(CacheStore2.class) ;
        Set<String> set = new HashSet<>() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = IdGen.uuid();
            CacheStore2 obj = build.build4k(randomKey) ;
            map.put(obj.getId(),obj) ;
            set.add(randomKey);
        }
        if (map.size()>0){
            long l1 = System.currentTimeMillis() ;
            igniteCache.putAll(map);
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
            map.clear();
            set.clear();
        }
        return cost ;
    }
}