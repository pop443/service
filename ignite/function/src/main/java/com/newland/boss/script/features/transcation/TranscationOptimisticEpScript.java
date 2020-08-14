package com.newland.boss.script.features.transcation;

import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache1Configuration;
import com.newland.boss.entity.transcation.TranscationCache2;
import com.newland.boss.entity.transcation.TranscationCache2Configuration;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;

import java.util.Random;

/**
 * 乐观锁
 */
public class TranscationOptimisticEpScript {
    private Ignite ignite ;
    private IgniteCache<String,TranscationCache1> igniteCache1;
    private IgniteCache<String,TranscationCache2> igniteCache2;
    private IgniteCache<String,BinaryObject> ic1 ;
    private IgniteCache<String,BinaryObject> ic2 ;

    public TranscationOptimisticEpScript() {
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,TranscationCache1> cacheConfiguration1 = new TranscationCache1Configuration().getCacheConfiguration() ;
        CacheConfiguration<String,TranscationCache2> cacheConfiguration2 = new TranscationCache2Configuration().getCacheConfiguration() ;
        ignite.destroyCache(cacheConfiguration1.getName());
        ignite.destroyCache(cacheConfiguration2.getName());
        igniteCache1 = ignite.createCache(cacheConfiguration1);
        igniteCache2 = ignite.createCache(cacheConfiguration2);
        ic1 = igniteCache1.withKeepBinary() ;
        ic2 = igniteCache2.withKeepBinary() ;
    }

    public void start() {
        Random random = new Random() ;
        boolean bo = random.nextBoolean() ;
        bo = true ;
        System.out.println("-----------标识位："+bo);
        TranscationCache1 transcationCache1 = new TranscationCache1("1","1") ;
        TranscationCache2 transcationCache2 = new TranscationCache2("1","1") ;
        IgniteTransactions transactions = ignite.transactions();
        Transaction tx = transactions.txStart(TransactionConcurrency.OPTIMISTIC,
                TransactionIsolation.SERIALIZABLE, 1000, 2);
        try {
            igniteCache1.put(transcationCache1.getId(),transcationCache1);
            boolean bo2 = ic2.invoke(transcationCache2.getId(), new PutEp1(),IgniteUtil.toBinary(transcationCache2)) ;
            System.out.println(bo2);
            if (bo){
                throw new Exception("");
            }
            tx.commit();
            System.out.println("------执行程序 正常提交-----");
        } catch (Exception e) {
            System.out.println("------中断程序 回滚-----");
            e.printStackTrace();
        }finally {
            tx.rollback();
            tx.close();
        }
        igniteCache1.close();
        igniteCache2.close();
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        //System.setProperty(IgniteSystemProperties.IGNITE_FORCE_MVCC_MODE_IN_TESTS, "true");
        System.out.println("---API 乐观锁---");
        TranscationOptimisticEpScript scirpt = new TranscationOptimisticEpScript();
        scirpt.start();
    }

}
