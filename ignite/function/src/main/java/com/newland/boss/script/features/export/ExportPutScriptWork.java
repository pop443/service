package com.newland.boss.script.features.export;

import com.newland.boss.entity.resource.FreeResource;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.utils.DiffString;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class ExportPutScriptWork extends PerformanceScriptWork<String, FreeResource> {
    public ExportPutScriptWork(EnterParam enterParam, IgniteCache<String, FreeResource> igniteCache, IgniteDataStreamer<String, FreeResource> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public long doing() {
        long cost = 0 ;
        Map<String,FreeResource> map = new HashMap<>() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = random.nextInt(enterParam.getCount())+enterParam.getCount()+"" ;
            FreeResource obj = new FreeResource(randomKey, DiffString.diffstr(4),random.nextInt(100)) ;
            map.put(obj.getId(),obj) ;
        }
        if (map.size()>0){
            long l1 = System.currentTimeMillis() ;
            igniteCache.putAll(map);
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
            map.clear();
        }
        return cost ;
    }
}