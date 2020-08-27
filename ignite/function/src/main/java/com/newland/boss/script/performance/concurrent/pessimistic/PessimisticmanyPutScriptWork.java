package com.newland.boss.script.performance.concurrent.pessimistic;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.transcation.*;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PessimisticmanyPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,TranscationCache1> igniteCache1 ;
    private IgniteCache<String,TranscationCache2> igniteCache2 ;
    private IgniteCache<String,TranscationCache3> igniteCache3 ;
    private IgniteCache<String,TranscationCache4> igniteCache4 ;
    private IgniteCache<String,TranscationCache5> igniteCache5 ;
    private Ignite ignite ;
    private Integer baseKey;
    public PessimisticmanyPutScriptWork(EnterParam enterParam, Ignite ignite, List<IgniteCache> list , Integer baseKey) {
        this.enterParam = enterParam;
        this.igniteCache1 = list.get(0) ;
        this.igniteCache2 = list.get(1) ;
        this.igniteCache3 = list.get(2) ;
        this.igniteCache4 = list.get(3) ;
        this.igniteCache5 = list.get(4) ;
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
        List<TranscationCache3> list3 = new ArrayList<>() ;
        List<TranscationCache4> list4 = new ArrayList<>() ;
        List<TranscationCache5> list5 = new ArrayList<>() ;
        CustObjBuild<TranscationCache1> build1 = new CustObjBuild<>(TranscationCache1.class) ;
        CustObjBuild<TranscationCache2> build2 = new CustObjBuild<>(TranscationCache2.class) ;
        CustObjBuild<TranscationCache3> build3 = new CustObjBuild<>(TranscationCache3.class) ;
        CustObjBuild<TranscationCache4> build4 = new CustObjBuild<>(TranscationCache4.class) ;
        CustObjBuild<TranscationCache5> build5 = new CustObjBuild<>(TranscationCache5.class) ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey1 = i+baseKey+"" ;
            String randomKey2 = i+baseKey+"" ;
            TranscationCache1 cache1 = build1.build4k(randomKey1) ;
            TranscationCache2 cache2 = build2.build4k(randomKey2) ;
            TranscationCache3 cache3 = build3.build4k(randomKey2) ;
            TranscationCache4 cache4 = build4.build4k(randomKey2) ;
            TranscationCache5 cache5 = build5.build4k(randomKey2) ;
            list1.add(cache1) ;
            list2.add(cache2) ;
            list3.add(cache3) ;
            list4.add(cache4) ;
            list5.add(cache5) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        IgniteTransactions igniteTransactions = ignite.transactions() ;
        for (int i = 0; i < list1.size(); i++) {
            Transaction tx = igniteTransactions.txStart(TransactionConcurrency.PESSIMISTIC,
                    TransactionIsolation.REPEATABLE_READ, 5000, 5);
            try {
                igniteCache1.put(list1.get(i).getId(),list1.get(i)) ;
                igniteCache2.put(list2.get(i).getId(),list2.get(i)) ;
                igniteCache3.put(list3.get(i).getId(),list3.get(i)) ;
                igniteCache4.put(list4.get(i).getId(),list4.get(i)) ;
                igniteCache5.put(list5.get(i).getId(),list5.get(i)) ;
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                tx.rollback();
                tx.close();
            }
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }
}
