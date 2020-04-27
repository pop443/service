package com.newland.boss.script.performance.rebalance;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.failover.PartitionLevel1;
import com.newland.boss.entity.performance.rebalance.Rebalance1;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class Rebalance1PutScriptWork extends PerformanceScriptWork<String, Rebalance1> {
    public Rebalance1PutScriptWork(EnterParam enterParam, IgniteCache<String, Rebalance1> igniteCache, IgniteDataStreamer<String, Rebalance1> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        long cost = 0 ;

        Map<String,Rebalance1> map = new HashMap<>() ;
        CustObjBuild<Rebalance1> build = new CustObjBuild<>(Rebalance1.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + baseKey + "";
            Rebalance1 obj = build.build1k(randomKey+"") ;
            map.put(obj.getId(),obj) ;
        }
        if (map.size() > 0) {
            long l1 = System.currentTimeMillis() ;
            ids.addData(map);
            ids.flush();
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
            map.clear();
        }
        return cost ;
    }

}
