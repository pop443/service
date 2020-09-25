package com.newland.boss.script.performance.affinityindexkey.cache1.put20;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.affinitykey.Cache1Key;
import com.newland.boss.entity.performance.affinitykey.Cache1Value;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class IndexPutScriptWork extends PerformanceScriptWork<Cache1Key,Cache1Value> {
    public IndexPutScriptWork(EnterParam enterParam, IgniteCache<Cache1Key,Cache1Value> igniteCache, IgniteDataStreamer<Cache1Key,Cache1Value> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<Cache1Value> build = new CustObjBuild<>(Cache1Value.class) ;
        Map<Cache1Key,Cache1Value> map = new HashMap<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            Cache1Value value = build.build1k(randomKey+"") ;
            for (int j = 0; j < 20; j++) {
                Cache1Key key = new Cache1Key(randomKey+j,randomKey) ;
                map.put(key,value) ;
            }
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (Map.Entry<Cache1Key,Cache1Value> entry:map.entrySet()) {
            igniteCache.put(entry.getKey(),entry.getValue());
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}