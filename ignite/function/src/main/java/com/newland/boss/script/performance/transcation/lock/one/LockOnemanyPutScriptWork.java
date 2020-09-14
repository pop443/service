package com.newland.boss.script.performance.transcation.lock.one;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache2;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

/**
 * Created by xz on 2020/3/10.
 */
public class LockOnemanyPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,TranscationCache1> igniteCache1 ;
    private IgniteCache<String,TranscationCache2> igniteCache2 ;
    private Ignite ignite ;
    private Integer baseKey;
    public LockOnemanyPutScriptWork(EnterParam enterParam, Ignite ignite, IgniteCache<String,TranscationCache1> igniteCache1, IgniteCache<String,TranscationCache2> igniteCache2, Integer baseKey) {
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
            String randomKey1 = baseKey+"" ;
            String randomKey2 = baseKey+"" ;
            TranscationCache1 bigObj1 = build1.build1k(randomKey1) ;
            TranscationCache2 smallObj1 = build2.build1k(randomKey2) ;
            list1.add(bigObj1) ;
            list2.add(smallObj1) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;

        for (int i = 0; i < list1.size(); i++) {
            TranscationCache1 cache1 = list1.get(i) ;
            TranscationCache2 cache2 = list2.get(i) ;
            Lock lock1 = igniteCache1.lock(cache1.getId()) ;
            Lock lock2 = igniteCache2.lock(cache2.getId()) ;
            try {
                lock1.lock();
                lock2.lock();
                igniteCache1.put(list1.get(i).getId(),list1.get(i)) ;
                igniteCache2.put(list2.get(i).getId(),list2.get(i)) ;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }
}