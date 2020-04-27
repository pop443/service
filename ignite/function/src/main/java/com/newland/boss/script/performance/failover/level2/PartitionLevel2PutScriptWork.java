package com.newland.boss.script.performance.failover.level2;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.failover.PartitionLevel2;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionLevel2PutScriptWork extends PerformanceScriptWork<String, PartitionLevel2> {
    public PartitionLevel2PutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionLevel2> igniteCache, IgniteDataStreamer<String, PartitionLevel2> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        long cost = 0 ;
        Map<String,PartitionLevel2> map = new HashMap<>() ;
        CustObjBuild<PartitionLevel2> build = new CustObjBuild<>(PartitionLevel2.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + baseKey + "";
            PartitionLevel2 obj = build.build1k(randomKey+"") ;
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
