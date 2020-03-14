package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import com.newland.boss.entity.performance.CustObjBuild;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallEpPutScriptAsynWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionSmallEpPutScriptAsynWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }


    @Override
    public void doing() {
        Map<String, PartitionCustObj> map = new HashMap<>();
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class);
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = random.nextInt(enterParam.getCount()) + enterParam.getCount() + "";
            if (map.size() == enterParam.getCommitSize()) {
                System.out.println("提交：" + map.size() + "条");
                epCommit(map);
                map.clear();
            }
            PartitionCustObj obj = build.build1k(randomKey + "");
            map.put(obj.getId(), obj);
        }
        if (map.size() > 0) {
            System.out.println("提交：" + map.size() + "条");
            epCommit(map);
            map.clear();
        }
    }

    private void epCommit(Map<String, PartitionCustObj> map) {
        IgniteFuture<Map<String, EntryProcessorResult<Boolean>>> future = igniteCache.invokeAllAsync(map.keySet(), new CacheEntryProcessor<String, PartitionCustObj, Boolean>() {
            @Override
            public Boolean process(MutableEntry<String, PartitionCustObj> mutableEntry, Object... objects) throws EntryProcessorException {
                boolean bo = false;
                try {
                    Map<String, PartitionCustObj> map = (Map<String, PartitionCustObj>) objects[0];
                    String key = mutableEntry.getKey();
                    mutableEntry.setValue(map.get(key));
                    bo = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bo;
            }
        }, map);
        Map<String, EntryProcessorResult<Boolean>> resultMap = future.get();
        resultMap.size();
    }
}
