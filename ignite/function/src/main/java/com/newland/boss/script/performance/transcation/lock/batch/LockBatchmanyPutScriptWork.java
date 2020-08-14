package com.newland.boss.script.performance.transcation.lock.batch;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache2;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

/**
 * Created by xz on 2020/3/10.
 */
public class LockBatchmanyPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,TranscationCache1> igniteCache1 ;
    private IgniteCache<String,TranscationCache2> igniteCache2 ;
    private Ignite ignite ;
    private Integer baseKey;
    public LockBatchmanyPutScriptWork(EnterParam enterParam, Ignite ignite, IgniteCache<String,TranscationCache1> igniteCache1, IgniteCache<String,TranscationCache2> igniteCache2, Integer baseKey) {
        this.enterParam = enterParam;
        this.igniteCache1 = igniteCache1 ;
        this.igniteCache2 = igniteCache2 ;
        this.baseKey = baseKey ;
        this.ignite = ignite ;
    }

    @Override
    public Long call() throws Exception {
        return working();
    }

    private Long working() {
        List<TranscationCache1> list1 = new ArrayList<>() ;
        List<TranscationCache2> list2 = new ArrayList<>() ;
        CustObjBuild<TranscationCache1> build1 = new CustObjBuild<>(TranscationCache1.class) ;
        CustObjBuild<TranscationCache2> build2 = new CustObjBuild<>(TranscationCache2.class) ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey1 = i+baseKey+"" ;
            String randomKey2 = i+baseKey+"" ;
            TranscationCache1 bigObj1 = build1.build1k(randomKey1) ;
            TranscationCache2 smallObj1 = build2.build1k(randomKey2) ;
            list1.add(bigObj1) ;
            list2.add(smallObj1) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        Map<String,TranscationCache1> map1 = new HashMap<>() ;
        Map<String,TranscationCache2> map2 = new HashMap<>() ;
        for (int i = 0; i < list1.size(); i++) {
            if (map1.size()==enterParam.getBatchSize()){
                commit(map1, map2);
            }
            TranscationCache1 cache1 = list1.get(i) ;
            TranscationCache2 cache2 = list2.get(i) ;
            map1.put(cache1.getId(),cache1) ;
            map2.put(cache2.getId(),cache2) ;
        }
        if (map1.size()>0){
            commit(map1, map2);
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }

    private void commit(Map<String, TranscationCache1> map1, Map<String, TranscationCache2> map2) {
        Lock lock1 = igniteCache1.lockAll(map1.keySet()) ;
        Lock lock2 = igniteCache2.lockAll(map2.keySet()) ;
        try {
            lock1.lock();
            lock2.lock();
            igniteCache1.putAll(map1);
            igniteCache2.putAll(map2) ;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock1.unlock();
            lock2.unlock();
            map1.clear();
            map2.clear();
        }
    }
}
