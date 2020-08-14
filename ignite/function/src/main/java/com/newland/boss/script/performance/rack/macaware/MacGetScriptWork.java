package com.newland.boss.script.performance.rack.macaware;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.rack.MacCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

/**
 * Created by xz on 2020/3/10.
 */
public class MacGetScriptWork extends PerformanceScriptWork<String, MacCustObj> {
    public MacGetScriptWork(EnterParam enterParam, IgniteCache<String, MacCustObj> igniteCache, IgniteDataStreamer<String, MacCustObj> igniteDataStreamer, Integer baseKey) {
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
