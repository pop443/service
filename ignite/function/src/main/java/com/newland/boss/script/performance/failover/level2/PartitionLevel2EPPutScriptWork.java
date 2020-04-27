package com.newland.boss.script.performance.failover.level2;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.failover.PartitionLevel2;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.lang.IgniteFuture;

import javax.cache.processor.EntryProcessorResult;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionLevel2EPPutScriptWork extends PerformanceScriptWork<String, PartitionLevel2> {
    private IgniteCache<String, BinaryObject> ic;

    public PartitionLevel2EPPutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionLevel2> igniteCache, IgniteDataStreamer<String, PartitionLevel2> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
        ic = igniteCache.withKeepBinary();
    }

    @Override
    public long doing() {
        long cost = 0;
        Map<String, BinaryObject> map = new HashMap<>();
        CustObjBuild<PartitionLevel2> build = new CustObjBuild<>(PartitionLevel2.class);
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            PartitionLevel2 obj = build.build1k(randomKey + "");
            map.put(obj.getId(), IgniteUtil.toBinary(obj));
        }
        if (map.size() > 0) {
            long l1 = System.currentTimeMillis();
            Map<String, EntryProcessorResult<Boolean>> map1 = ic.invokeAll(map.keySet(), new PutEp1(), map);
            long l2 = System.currentTimeMillis();
            cost = cost + (l2 - l1);
            map.clear();
        }
        return cost;
    }
}
