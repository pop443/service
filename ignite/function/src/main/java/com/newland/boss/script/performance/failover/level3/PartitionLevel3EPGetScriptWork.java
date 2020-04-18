package com.newland.boss.script.performance.failover.level3;

import com.newland.boss.entity.performance.failover.PartitionLevel2;
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
public class PartitionLevel3EPGetScriptWork extends PerformanceScriptWork<String, PartitionLevel2> {
    private IgniteCache<String,BinaryObject> igniteCache2  ;
    public PartitionLevel3EPGetScriptWork(EnterParam enterParam, IgniteCache<String, PartitionLevel2> igniteCache, IgniteDataStreamer<String, PartitionLevel2> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
        igniteCache2 = igniteCache.withKeepBinary() ;
    }


    @Override
    public void doing() {
        Set<String> set = new HashSet<>(enterParam.getCommitSize()) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = random.nextInt(enterParam.getCount())+enterParam.getCount()+"" ;
            set.add(randomKey);
            if (set.size()==enterParam.getCommitSize()){
                int getCount = epGet(set);
                System.out.println(Thread.currentThread().getName()+"读取"+enterParam.getCommitSize()+"条:实际获取"+getCount+"条");
                set.clear();
            }
        }
        if (set.size()>0){
            int getCount = epGet(set);
            System.out.println(Thread.currentThread().getName()+"读取"+enterParam.getCommitSize()+"条:实际获取"+getCount+"条");
            set.clear();
        }
    }

    private int epGet(Set<String> set) {
        IgniteFuture<Map<String, EntryProcessorResult<BinaryObject>>> future = igniteCache2.invokeAllAsync(set, new CacheEntryProcessor<String, BinaryObject, BinaryObject>() {
            @Override
            public BinaryObject process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
                return mutableEntry.getValue();
            }
        });
        Map<String, EntryProcessorResult<BinaryObject>> map = future.get();
        set.clear();
        return map.size();
    }
}
