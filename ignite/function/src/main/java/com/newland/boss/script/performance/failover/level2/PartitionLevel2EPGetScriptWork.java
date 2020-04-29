package com.newland.boss.script.performance.failover.level2;

import com.newland.boss.entity.performance.failover.PartitionLevel2;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.GetEp1;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionLevel2EPGetScriptWork extends PerformanceScriptWork<String, PartitionLevel2> {
    private IgniteCache<String, BinaryObject> igniteCache2;

    public PartitionLevel2EPGetScriptWork(EnterParam enterParam, IgniteCache<String, PartitionLevel2> igniteCache, IgniteDataStreamer<String, PartitionLevel2> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        igniteCache2 = igniteCache.withKeepBinary();
    }


    @Override
    public long doing() {
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            igniteCache2.invoke(randomKey,new GetEp1()) ;
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }

}
