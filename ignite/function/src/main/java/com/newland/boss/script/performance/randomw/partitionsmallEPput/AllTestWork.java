package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.IgniteException;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.lang.IgniteFutureTimeoutException;
import org.apache.ignite.transactions.TransactionException;

import javax.cache.processor.EntryProcessorResult;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2020/4/30.
 */
public class AllTestWork extends PerformanceScriptWork<String, Expiry> {
    private IgniteCache<String, BinaryObject> ic;

    public AllTestWork(EnterParam enterParam, IgniteCache<String, Expiry> igniteCache, IgniteDataStreamer<String, Expiry> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer, baseKey);
        ic = igniteCache.withKeepBinary();
        System.out.println(igniteCache);
    }


    @Override
    public long doing() {
        try {
            //System.out.println("休眠");
            //Thread.sleep(5000L);
            Map<String,BinaryObject> map = new LinkedHashMap<>() ;
            //Map<String,Expiry> map2 = new HashMap<>() ;
            for (int i = 0; i < 1000 ; i++) {
                String randomKey = i + "";
                Expiry obj1 = new Expiry();
                obj1.setId(randomKey);
                obj1.setAutomation(new Automation(randomKey, 1, randomKey));
                //System.out.println(obj1);
                //ic.invoke(obj1.getId(), new PutEp1(), IgniteUtil.toBinary(obj1));
                //igniteCache.get(j+"");
                map.put(obj1.getId(),IgniteUtil.toBinary(obj1));
                //map2.put(obj1.getId(),obj1);

            }
            Map<String,EntryProcessorResult<Boolean>> mapAll = ic.invokeAll(map.keySet(),new PutEp2(),map);
            Iterator<Map.Entry<String,EntryProcessorResult<Boolean>>> it =  mapAll.entrySet().iterator() ;
            while (it.hasNext()){
                Map.Entry<String,EntryProcessorResult<Boolean>> entry = it.next() ;
                System.out.println(entry.getKey()+"--"+entry.getValue().get());
            }
            //igniteCache.putAll(map2);
            //igniteCache.getAll(map2.keySet());
            //ids.addData(map2);
            //ids.flush();
            //System.out.println("休眠");
            //Thread.sleep(10000L);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
