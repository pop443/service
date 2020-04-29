package com.newland.boss.script.performance.rebalance;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.rebalance.Rebalance4;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

/**
 * Created by xz on 2020/3/10.
 */
public class Rebalance4PutScriptWork extends PerformanceScriptWork<String, Rebalance4> {
    public Rebalance4PutScriptWork(EnterParam enterParam, IgniteCache<String, Rebalance4> igniteCache, IgniteDataStreamer<String, Rebalance4> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        long l1 = System.currentTimeMillis() ;
        CustObjBuild<Rebalance4> build = new CustObjBuild<>(Rebalance4.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + baseKey + "";
            Rebalance4 obj = build.build1k(randomKey+"") ;
            igniteCache.put(obj.getId(),obj) ;
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }

}
