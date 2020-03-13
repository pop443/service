package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionSmallCustObj;
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
    private int eachSize ;
    private int commitSize ;
    private IgniteCache<String, PartitionSmallCustObj> igniteCache ;
    private Random random ;
    private int count ;
    public PartitionSmallEpPutScriptWork(int eachSize, int count, IgniteCache<String, PartitionSmallCustObj> igniteCache) {
        this.eachSize = eachSize ;
        this.random = new Random() ;
        this.count = count ;
        this.igniteCache = igniteCache ;
        this.commitSize = Constant.batchSize ;
    }

    @Override
    public Long call() throws Exception {
        Long l1 = System.currentTimeMillis() ;
        try {
            working();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }

    private void working() {
        Map<String,PartitionSmallCustObj> map = new HashMap<>() ;
        CustObjBuild<PartitionSmallCustObj> build = new CustObjBuild<>(PartitionSmallCustObj.class) ;
        for (int i = 0; i < eachSize; i++) {
            String randomKey = random.nextInt(count)+count+"" ;
            if (map.size()==commitSize){
                epCommit(map);
                System.out.println("提交"+commitSize+"条");
            }
            PartitionSmallCustObj obj = build.build1k(randomKey+"") ;
            map.put(obj.getId(),obj) ;
        }
        System.out.println("----"+map.size());
        if (map.size()>0){
            epCommit(map);
        }
    }

    private void epCommit(Map<String, PartitionSmallCustObj> map) {
        Map<String,EntryProcessorResult<Boolean>> resultMap = igniteCache.invokeAll(map.keySet(), new CacheEntryProcessor<String, PartitionSmallCustObj, Boolean>() {
            @Override
            public Boolean process(MutableEntry<String, PartitionSmallCustObj> mutableEntry, Object... objects) throws EntryProcessorException {
                boolean bo = false ;
                try {
                    Map<String, PartitionSmallCustObj> map = (Map<String, PartitionSmallCustObj>)objects[0];
                    String key = mutableEntry.getKey();
                    mutableEntry.setValue(map.get(key));
                    bo=true ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bo;
            }
        },map);
        resultMap.forEach((key,epResult)->{
            if (!epResult.get()){
                System.out.println("失败："+key);
            }
        });
        map.clear();
    }
}
