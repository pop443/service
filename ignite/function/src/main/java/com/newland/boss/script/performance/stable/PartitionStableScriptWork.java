package com.newland.boss.script.performance.stable;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.label.utils.IdGen;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.transactions.TransactionException;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import javax.cache.processor.MutableEntry;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionStableScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    private IgniteCache<String,BinaryObject> ic = null ;
    private AtomicLong atomicLong ;

    public PartitionStableScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        ic = igniteCache.withKeepBinary();
        atomicLong = new AtomicLong(0) ;
    }

    @Override
    public long doing() {
        Timer timer = new Timer("Stable") ;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Long count = atomicLong.getAndSet(0);
                System.out.println(Thread.currentThread().getName()+"循环次数："+count+",置0");
            }
        }, 10*60*1000,10*60*1000);

        Long l1 = System.currentTimeMillis();
        while (System.currentTimeMillis() - l1 < 2*60*60*1000) {
            //while (System.currentTimeMillis() - l1 < 10*1000) {
            atomicLong.addAndGet(1);
            try {
                Map<String, PartitionCustObj> map = new HashMap<>();
                Map<String, BinaryObject> map2 = new HashMap<>();
                CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class);
                Set<String> set = new HashSet<>() ;
                for (int i = 0; i < enterParam.getCount(); i++) {
                    String randomKey = baseKey + enterParam.getCount() + "";
                    PartitionCustObj obj = build.build1k(randomKey + "");
                    map.put(obj.getId(), obj);
                    map2.put(obj.getId(), IgniteUtil.toBinary(obj));
                    set.add(randomKey);
                }
                if (map.size() > 0) {
                    igniteCache.putAll(map);
                    igniteCache.getAll(set).size();
                    epCommit(map2);
                    epGet(set);
                    map2.clear();
                    map.clear();
                    set.clear();
                }

            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    private void epCommit(Map<String, BinaryObject> map) {
        Map<String, EntryProcessorResult<Boolean>> resultMap = ic.invokeAll(map.keySet(), new PutEp1(), map);
        resultMap.size();
    }

    private int epGet(Set<String> set) {
        Map<String, EntryProcessorResult<PartitionCustObj>> map = igniteCache.invokeAll(set, new CacheEntryProcessor<String, PartitionCustObj, PartitionCustObj>() {
            @Override
            public PartitionCustObj process(MutableEntry<String, PartitionCustObj> mutableEntry, Object... objects) throws EntryProcessorException {
                return mutableEntry.getValue();
            }
        });
        set.clear();
        return map.size();

    }
}
