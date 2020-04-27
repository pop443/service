package com.newland.boss.script.performance.randomr.partitionsmallgetsamekey;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallGetSameKeyScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionSmallGetSameKeyScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        long cost = 0;
        long l1 = System.currentTimeMillis();
        igniteCache.get("1");
        long l2 = System.currentTimeMillis();
        cost = cost + (l2 - l1);
        return cost;
    }
}
