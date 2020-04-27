package com.newland.boss.script.performance.failover.level3;

import com.newland.boss.entity.performance.failover.PartitionLevel3;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionLevel3GetScriptWork extends PerformanceScriptWork<String, PartitionLevel3> {
    public PartitionLevel3GetScriptWork(EnterParam enterParam, IgniteCache<String, PartitionLevel3> igniteCache, IgniteDataStreamer<String, PartitionLevel3> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        long cost = 0;
        Set<String> set = new HashSet<>(enterParam.getCommitSize());
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + baseKey + "";
            set.add(randomKey);
        }
        if (set.size() > 0) {
            long l1 = System.currentTimeMillis();
            int getCount = igniteCache.getAll(set).size();
            long l2 = System.currentTimeMillis();
            cost = cost + (l2 - l1);
            set.clear();
        }
        return cost ;
    }
}
