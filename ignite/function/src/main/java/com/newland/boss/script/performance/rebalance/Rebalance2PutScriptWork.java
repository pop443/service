package com.newland.boss.script.performance.rebalance;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.rebalance.Rebalance1;
import com.newland.boss.entity.performance.rebalance.Rebalance2;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class Rebalance2PutScriptWork extends PerformanceScriptWork<String, Rebalance2> {
    public Rebalance2PutScriptWork(EnterParam enterParam, IgniteCache<String, Rebalance2> igniteCache, IgniteDataStreamer<String, Rebalance2> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<Rebalance2> build = new CustObjBuild<>(Rebalance2.class) ;
        List<Rebalance2> list = new ArrayList<>() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            Rebalance2 obj = build.build1k(randomKey+"") ;
            list.add(obj);
        }
        long l1 = System.currentTimeMillis() ;
        for (Rebalance2 obj:list) {
            igniteCache.put(obj.getId(),obj) ;
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }

}
