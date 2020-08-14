package com.newland.boss.script.performance.rack.rackaware;

import com.newland.boss.entity.performance.rack.MacCustObj;
import com.newland.boss.entity.performance.rack.Rack1CustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

/**
 * Created by xz on 2020/3/10.
 */
public class Rack1GetScriptWork extends PerformanceScriptWork<String, Rack1CustObj> {
    public Rack1GetScriptWork(EnterParam enterParam, IgniteCache<String, Rack1CustObj> igniteCache, IgniteDataStreamer<String, Rack1CustObj> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            igniteCache.get(randomKey);
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}
