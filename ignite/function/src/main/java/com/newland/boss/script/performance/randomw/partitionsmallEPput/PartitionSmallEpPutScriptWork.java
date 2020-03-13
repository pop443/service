package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionSmallCustObj;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheEntryProcessor;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import javax.cache.processor.MutableEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallEpPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String, PartitionSmallCustObj> igniteCache;
    private Random random;

    public PartitionSmallEpPutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionSmallCustObj> igniteCache) {
        this.random = new Random();
        this.enterParam = enterParam;
        this.igniteCache = igniteCache;
    }

    @Override
    public Long call() throws Exception {
        Long l1 = System.currentTimeMillis();
        try {
            working();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long l2 = System.currentTimeMillis();
        return l2 - l1;
    }

    private void working() {
        Map<String, PartitionSmallCustObj> map = new HashMap<>();
        CustObjBuild<PartitionSmallCustObj> build = new CustObjBuild<>(PartitionSmallCustObj.class);
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = random.nextInt(enterParam.getCount()) + enterParam.getCount() + "";
            if (map.size() == enterParam.getCommitSize()) {
                System.out.println("提交：" + map.size() + "条");
                epCommit(map);
                map.clear();
            }
            PartitionSmallCustObj obj = build.build1k(randomKey + "");
            map.put(obj.getId(), obj);
        }
        if (map.size() > 0) {
            System.out.println("提交：" + map.size() + "条");
            epCommit(map);
            map.clear();
        }
    }

    private void epCommit(Map<String, PartitionSmallCustObj> map) {
        Map<String, EntryProcessorResult<Boolean>> resultMap = igniteCache.invokeAll(map.keySet(), new CacheEntryProcessor<String, PartitionSmallCustObj, Boolean>() {
            @Override
            public Boolean process(MutableEntry<String, PartitionSmallCustObj> mutableEntry, Object... objects) throws EntryProcessorException {
                boolean bo = false;
                try {
                    Map<String, PartitionSmallCustObj> map = (Map<String, PartitionSmallCustObj>) objects[0];
                    String key = mutableEntry.getKey();
                    mutableEntry.setValue(map.get(key));
                    bo = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bo;
            }
        }, map);
        resultMap.forEach((key, epResult) -> {
            if (!epResult.get()) {
                System.out.println("失败：" + key);
            }
        });
    }
}
