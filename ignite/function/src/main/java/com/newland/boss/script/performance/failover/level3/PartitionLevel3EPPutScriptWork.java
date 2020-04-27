package com.newland.boss.script.performance.failover.level3;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.failover.PartitionLevel3;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;

import javax.cache.processor.EntryProcessorResult;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionLevel3EPPutScriptWork extends PerformanceScriptWork<String, PartitionLevel3> {
    private IgniteCache<String,BinaryObject> ic ;
    public PartitionLevel3EPPutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionLevel3> igniteCache, IgniteDataStreamer<String, PartitionLevel3> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        ic = igniteCache.withKeepBinary() ;
    }

    @Override
    public long doing() {
        long cost = 0 ;
        Map<String, BinaryObject> map = new HashMap<>();
        CustObjBuild<PartitionLevel3> build = new CustObjBuild<>(PartitionLevel3.class);
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + baseKey + "";
            PartitionLevel3 obj = build.build1k(randomKey + "");
            map.put(obj.getId(), IgniteUtil.toBinary(obj));
        }
        if (map.size() > 0) {
            long l1 = System.currentTimeMillis() ;
            Map<String, EntryProcessorResult<Boolean>> map1 = ic.invokeAll(map.keySet(), new PutEp1(), map);
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
            map.clear();
        }
        return cost ;
    }

}
