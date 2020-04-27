package com.newland.boss.script.performance.failover.level3;

import com.newland.boss.entity.performance.failover.PartitionLevel3;
import com.newland.boss.entity.performance.failover.PartitionLevel3;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.lang.IgniteFuture;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import javax.cache.processor.MutableEntry;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionLevel3EPGetScriptWork extends PerformanceScriptWork<String, PartitionLevel3> {
    private IgniteCache<String, BinaryObject> igniteCache2;

    public PartitionLevel3EPGetScriptWork(EnterParam enterParam, IgniteCache<String, PartitionLevel3> igniteCache, IgniteDataStreamer<String, PartitionLevel3> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
        igniteCache2 = igniteCache.withKeepBinary();
    }


    @Override
    public long doing() {
        long cost = 0;
        Set<String> set = new HashSet<>(enterParam.getCommitSize());
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            set.add(randomKey);
        }
        if (set.size() > 0) {
            long l1 = System.currentTimeMillis();
            Map<String, EntryProcessorResult<BinaryObject>> map = igniteCache2.invokeAll(set, new CacheEntryProcessor<String, BinaryObject, BinaryObject>() {
                @Override
                public BinaryObject process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
                    return mutableEntry.getValue();
                }
            });
            long l2 = System.currentTimeMillis();
            cost = cost + (l2 - l1);
            set.clear();
        }
        return cost;
    }

}
