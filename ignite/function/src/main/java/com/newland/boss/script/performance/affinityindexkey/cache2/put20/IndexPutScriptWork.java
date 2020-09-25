package com.newland.boss.script.performance.affinityindexkey.cache2.put20;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.affinitykey.Cache2Key;
import com.newland.boss.entity.performance.affinitykey.Cache2Value;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class IndexPutScriptWork extends PerformanceScriptWork<Cache2Key,Cache2Value> {
    public IndexPutScriptWork(EnterParam enterParam, IgniteCache<Cache2Key,Cache2Value> igniteCache, IgniteDataStreamer<Cache2Key,Cache2Value> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<Cache2Value> build = new CustObjBuild<>(Cache2Value.class) ;
        Map<Cache2Key,Cache2Value> map = new HashMap<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            Cache2Value value = build.build1k(randomKey+"") ;
            for (int j = 0; j < 20; j++) {
                Cache2Key key = new Cache2Key(randomKey+j,randomKey) ;
                map.put(key,value) ;
            }
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (Map.Entry<Cache2Key,Cache2Value> entry:map.entrySet()) {
            igniteCache.put(entry.getKey(),entry.getValue());
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}