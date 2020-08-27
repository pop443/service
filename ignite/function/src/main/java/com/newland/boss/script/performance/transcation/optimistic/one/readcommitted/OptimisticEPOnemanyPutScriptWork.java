package com.newland.boss.script.performance.transcation.optimistic.one.readcommitted;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache2;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class OptimisticEPOnemanyPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,TranscationCache1> igniteCache1 ;
    private IgniteCache<String,TranscationCache2> igniteCache2 ;
    private IgniteCache<String,BinaryObject> ic1 ;
    private IgniteCache<String,BinaryObject> ic2 ;
    private Ignite ignite ;
    private Integer baseKey;
    public OptimisticEPOnemanyPutScriptWork(EnterParam enterParam, Ignite ignite, IgniteCache<String,TranscationCache1> igniteCache1, IgniteCache<String,TranscationCache2> igniteCache2, Integer baseKey) {
        this.enterParam = enterParam;
        this.igniteCache1 = igniteCache1 ;
        this.igniteCache2 = igniteCache2 ;
        this.baseKey = baseKey ;
        this.ignite = ignite ;
        this.ic1 = igniteCache1.withKeepBinary() ;
        this.ic2 = igniteCache2.withKeepBinary() ;
    }

    @Override
    public Long call() throws Exception {
        return working();
    }

    private Long working() {
        List<BinaryObject> list1 = new ArrayList<>() ;
        List<BinaryObject> list2 = new ArrayList<>() ;
        CustObjBuild<TranscationCache1> build1 = new CustObjBuild<>(TranscationCache1.class) ;
        CustObjBuild<TranscationCache2> build2 = new CustObjBuild<>(TranscationCache2.class) ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey1 = baseKey+"" ;
            String randomKey2 = baseKey+"" ;
            TranscationCache1 bigObj1 = build1.build1k(randomKey1) ;
            TranscationCache2 smallObj1 = build2.build1k(randomKey2) ;
            list1.add(IgniteUtil.toBinary(bigObj1)) ;
            list2.add(IgniteUtil.toBinary(smallObj1)) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        IgniteTransactions igniteTransactions = ignite.transactions() ;
        for (int i = 0; i < list1.size(); i++) {
            Transaction tx = igniteTransactions.txStart(TransactionConcurrency.OPTIMISTIC,
                    TransactionIsolation.READ_COMMITTED, 5000, 2);
            try {
                ic1.invoke(list1.get(i).field("id"),new PutEp1(),list1.get(i)) ;
                ic2.invoke(list2.get(i).field("id"),new PutEp1(),list2.get(i)) ;
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
