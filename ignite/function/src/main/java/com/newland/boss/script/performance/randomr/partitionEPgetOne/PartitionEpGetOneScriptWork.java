package com.newland.boss.script.performance.randomr.partitionEPgetOne;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
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
public class PartitionEpGetOneScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionEpGetOneScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }



    @Override
    public long doing() {
        long cost = 0 ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+enterParam.getCount()+"" ;
            long l1 = System.currentTimeMillis() ;
            PartitionCustObj partitionCustObj = igniteCache.invoke(randomKey, new CacheEntryProcessor<String, PartitionCustObj, PartitionCustObj>() {
                @Override
                public PartitionCustObj process(MutableEntry<String, PartitionCustObj> mutableEntry, Object... objects) throws EntryProcessorException {
                    return mutableEntry.getValue();
                }
            });
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
        }
        return cost ;
    }

}
