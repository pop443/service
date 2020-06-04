package com.newland.boss.script.performance.stable;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.cachestore.CacheStore1;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.GetEp1;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.label.utils.IdGen;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.transactions.TransactionException;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by xz on 2020/3/10.
 */
public class EPStableScriptWork extends PerformanceScriptWork<String, CacheStore1> {
    private AtomicLong atomicLong ;
    public EPStableScriptWork(EnterParam enterParam, IgniteCache<String, CacheStore1> igniteCache, IgniteDataStreamer<String, CacheStore1> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        atomicLong = new AtomicLong(0) ;
    }

    @Override
    public long doing() {
        System.out.println("start-------------------");
        Timer timer = new Timer("Stable") ;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Long count = atomicLong.getAndSet(0);
                System.out.println(Thread.currentThread().getName()+"循环次数："+count+",置0");
            }
        }, 60*1000,60*1000);

        Long l1 = System.currentTimeMillis();
        while ((System.currentTimeMillis() - l1) < 2*60*60*1000) {
            //while (System.currentTimeMillis() - l1 < 10*1000) {
            atomicLong.addAndGet(1);
            try {
                CustObjBuild<CacheStore1> build = new CustObjBuild<>(CacheStore1.class);
                for (int i = 0; i < enterParam.getCount(); i++) {
                    String randomKey = IdGen.uuid();
                    CacheStore1 obj = build.build1k(randomKey + "");
                    igniteCache.invoke(obj.getId(), new EntryProcessor<String, CacheStore1, Object>() {
                        @Override
                        public Object process(MutableEntry<String, CacheStore1> mutableEntry, Object... objects) throws EntryProcessorException {
                            CacheStore1 cacheStore1 = (CacheStore1)objects[0] ;
                            mutableEntry.setValue(cacheStore1);
                            return null;
                        }
                    },obj);
                }
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }
        timer.cancel();
        timer = null ;
        return 0;
    }
}
