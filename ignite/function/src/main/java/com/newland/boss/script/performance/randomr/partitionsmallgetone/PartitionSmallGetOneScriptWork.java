package com.newland.boss.script.performance.randomr.partitionsmallgetone;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallGetOneScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionSmallGetOneScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public long doing() {
        long cost = 0 ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+enterParam.getCount()+"" ;
            long l1 = System.currentTimeMillis() ;
            igniteCache.get(randomKey);
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
        }
        return cost ;
    }
}
