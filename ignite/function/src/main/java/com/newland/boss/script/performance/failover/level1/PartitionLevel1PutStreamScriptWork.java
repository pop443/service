package com.newland.boss.script.performance.failover.level1;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.failover.PartitionLevel1;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionLevel1PutStreamScriptWork extends PerformanceScriptWork<String, PartitionLevel1> {
    public PartitionLevel1PutStreamScriptWork(EnterParam enterParam, IgniteCache<String, PartitionLevel1> igniteCache, IgniteDataStreamer<String, PartitionLevel1> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        long cost = 0;
        Map<String, PartitionLevel1> map = new HashMap<>();
        CustObjBuild<PartitionLevel1> build = new CustObjBuild<>(PartitionLevel1.class);
        for (int i = 0; i < enterParam.getCount(); i++) {
            if (map.size()==enterParam.getBatchSize()){
                ids.addData(map);
                ids.flush();
                map.clear();
            }
            String randomKey = i + baseKey + "";
            PartitionLevel1 obj = build.build1k(randomKey + "");
            map.put(obj.getId(), obj);
        }
        if (map.size() > 0) {
            long l1 = System.currentTimeMillis();
            ids.addData(map);
            ids.flush();
            long l2 = System.currentTimeMillis();
            cost = cost + (l2 - l1);
            map.clear();
        }
        return cost;
    }

}
