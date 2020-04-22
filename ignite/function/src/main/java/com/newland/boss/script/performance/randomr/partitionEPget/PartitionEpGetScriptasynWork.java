package com.newland.boss.script.performance.randomr.partitionEPget;

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
public class PartitionEpGetScriptasynWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionEpGetScriptasynWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }
    @Override
    public Long call() throws Exception {
        Long l1 = System.currentTimeMillis() ;
        long jian = doing1();
        Long l2 = System.currentTimeMillis() ;
        return l2-l1-jian;
    }

    @Override
    public void doing() throws Exception {

    }

    public long doing1() {
        long jian = 0 ;
        Set<String> set = new HashSet<>(enterParam.getCommitSize()) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = random.nextInt(enterParam.getCount())+enterParam.getCount()+"" ;
            set.add(randomKey);
        }
        if (set.size()>0){
            jian = epGet(set);
            set.clear();
        }
        return jian ;
    }

    private long epGet(Set<String> set) {
        IgniteFuture<Map<String, EntryProcessorResult<PartitionCustObj>>> future = igniteCache.invokeAllAsync(set, new CacheEntryProcessor<String, PartitionCustObj, PartitionCustObj>() {
            @Override
            public PartitionCustObj process(MutableEntry<String, PartitionCustObj> mutableEntry, Object... objects) throws EntryProcessorException {
                return mutableEntry.getValue();
            }
        });
        Long l1 = System.currentTimeMillis() ;
        Map<String, EntryProcessorResult<PartitionCustObj>> map = future.get();
        Long l2 = System.currentTimeMillis() ;
        set.clear();
        return l2-l1;

    }
}
