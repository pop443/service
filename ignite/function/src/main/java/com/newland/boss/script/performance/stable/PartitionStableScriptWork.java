package com.newland.boss.script.performance.stable;

import com.newland.boss.entity.performance.CustObjBuild;
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
        System.out.println("start-------------------");
        Timer timer = new Timer("Stable") ;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Long count = atomicLong.getAndSet(0);
                System.out.println(Thread.currentThread().getName()+"循环次数："+count+",置0");
            }
        }, 10*60*1000,10*60*1000);

        Long l1 = System.currentTimeMillis();
        while ((System.currentTimeMillis() - l1) < 2*60*60*1000) {
            //while (System.currentTimeMillis() - l1 < 10*1000) {
            atomicLong.addAndGet(1);
            try {
                CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class);
                for (int i = 0; i < enterParam.getCount(); i++) {
                    String randomKey = IdGen.uuid();
                    PartitionCustObj obj = build.build1k(randomKey + "");
                    ic.invoke(obj.getId(),new PutEp1(),IgniteUtil.toBinary(obj));
                    ic.invoke(obj.getId(),new GetEp1());
                    igniteCache.put(obj.getId(),obj);
                    igniteCache.get(obj.getId());
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
