package com.newland.boss.script.features.transcation;

import com.newland.boss.entity.transcation.aff.*;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.InnerEpAff;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;

/**
 * 乐观锁
 */
public class TranscationInnerEpWithAffScript {
    private Ignite ignite ;
    private IgniteCache<TranscationCacheAff1Key,TranscationCacheAff1> igniteCache1;
    private IgniteCache<String,TranscationCacheAff2> igniteCache2;
    private IgniteCache<BinaryObject,BinaryObject> ic1 ;
    private IgniteCache<String,BinaryObject> ic2 ;

    public TranscationInnerEpWithAffScript() {
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<TranscationCacheAff1Key,TranscationCacheAff1> cacheConfiguration1 = new TranscationCacheAff1Configuration().getCacheConfiguration() ;
        CacheConfiguration<String,TranscationCacheAff2> cacheConfiguration2 = new TranscationCacheAff2Configuration().getCacheConfiguration() ;
        igniteCache1 = ignite.getOrCreateCache(cacheConfiguration1);
        igniteCache2 = ignite.getOrCreateCache(cacheConfiguration2);
        ic1 = igniteCache1.withKeepBinary() ;
        ic2 = igniteCache2.withKeepBinary() ;
    }

    public void start() {
        String key = "66" ;
        TranscationCacheAff1 transcationCacheAff1 = new TranscationCacheAff1(key,key) ;
        TranscationCacheAff1Key transcationCacheAff1Key = new TranscationCacheAff1Key(key,key) ;
        TranscationCacheAff2 transcationCacheAff2 = new TranscationCacheAff2(key,key) ;
        IgniteTransactions transactions = ignite.transactions();
        Transaction tx = transactions.txStart(TransactionConcurrency.PESSIMISTIC,
                TransactionIsolation.SERIALIZABLE, 1000, 2);
        try {
            boolean bo = ic1.invoke(IgniteUtil.toBinary(transcationCacheAff1Key),new InnerEpAff(ic2),IgniteUtil.toBinary(transcationCacheAff1),IgniteUtil.toBinary(transcationCacheAff2)) ;
            System.out.println(bo);
            if (bo){
                throw new Exception("111");
            }
            tx.commit();
        } catch (Exception e) {
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
        TranscationInnerEpWithAffScript scirpt = new TranscationInnerEpWithAffScript();
        scirpt.start();
    }

}
