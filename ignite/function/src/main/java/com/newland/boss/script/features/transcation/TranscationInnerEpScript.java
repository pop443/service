package com.newland.boss.script.features.transcation;

import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache1Configuration;
import com.newland.boss.entity.transcation.TranscationCache2;
import com.newland.boss.entity.transcation.TranscationCache2Configuration;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.InnerEp;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.InnerEpUseEp;
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
public class TranscationInnerEpScript {
    private Ignite ignite ;
    private IgniteCache<String,TranscationCache1> igniteCache1;
    private IgniteCache<String,TranscationCache2> igniteCache2;
    private IgniteCache<String,BinaryObject> ic1 ;
    private IgniteCache<String,BinaryObject> ic2 ;

    public TranscationInnerEpScript() {
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,TranscationCache1> cacheConfiguration1 = new TranscationCache1Configuration().getCacheConfiguration() ;
        CacheConfiguration<String,TranscationCache2> cacheConfiguration2 = new TranscationCache2Configuration().getCacheConfiguration() ;
        igniteCache1 = ignite.getOrCreateCache(cacheConfiguration1);
        igniteCache2 = ignite.getOrCreateCache(cacheConfiguration2);
        ic1 = igniteCache1.withKeepBinary() ;
        ic2 = igniteCache2.withKeepBinary() ;
    }

    public void start() {
        String key = "5" ;
        TranscationCache1 transcationCache1 = new TranscationCache1(key,key) ;
        TranscationCache2 transcationCache2 = new TranscationCache2(key,key) ;
        IgniteTransactions transactions = ignite.transactions();
        Transaction tx = transactions.txStart(TransactionConcurrency.OPTIMISTIC,
                TransactionIsolation.SERIALIZABLE, 1000, 2);
        try {
            boolean bo = ic1.invoke(transcationCache1.getId(),new InnerEpUseEp(ic2),IgniteUtil.toBinary(transcationCache1),IgniteUtil.toBinary(transcationCache2)) ;
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
        TranscationInnerEpScript scirpt = new TranscationInnerEpScript();
        scirpt.start();
    }

}
