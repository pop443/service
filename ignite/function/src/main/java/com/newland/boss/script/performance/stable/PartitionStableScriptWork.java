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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionStableScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    private IgniteCache<String,BinaryObject> ic = null ;
    public PartitionStableScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
        ic = igniteCache.withKeepBinary();
    }

    @Override
    public void doing() {
        long count = 0 ;
        Long l1 = System.currentTimeMillis();
        while (System.currentTimeMillis() - l1 < 30*60*1000) {
            count = count + 1 ;
            System.out.println("循环次数："+count);
        //while (System.currentTimeMillis() - l1 < 10*1000) {
            try {
                Map<String, PartitionCustObj> map = new HashMap<>();
                Map<String, BinaryObject> map2 = new HashMap<>();
                CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class);
                Set<String> set = new HashSet<>() ;
                for (int i = 0; i < enterParam.getCount(); i++) {
                    String randomKey = random.nextInt(enterParam.getCount()) + enterParam.getCount() + "";;
                    PartitionCustObj obj = build.build4k(randomKey + "");
                    map.put(obj.getId(), obj);
                    map2.put(obj.getId(), IgniteUtil.toBinary(obj));
                    set.add(randomKey);
                }
                if (map.size() > 0) {
                    igniteCache.putAll(map);
                    System.out.println("读取：" + igniteCache.getAll(set).size() + "条");
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


    }
    private void epCommit(Map<String, BinaryObject> map) {
        Map<String, EntryProcessorResult<Boolean>> resultMap = ic.invokeAll(map.keySet(), new PutEp1(), map);
        resultMap.size();
        System.out.println("ok");
    }

    private int epGet(Set<String> set) {
        System.out.println("--"+set.size());
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
