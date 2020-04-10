package com.newland.boss.script.features.transcation;

import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache1Configuration;
import com.newland.boss.entity.transcation.TranscationCache2;
import com.newland.boss.entity.transcation.TranscationCache2Configuration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteSystemProperties;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;

import java.util.Random;

/**
 * 3.1.1 导出能力
 */
public class TranscationOptimisticScript {
    private Ignite ignite ;
    private IgniteCache<String,TranscationCache1> igniteCache1;
    private IgniteCache<String,TranscationCache2> igniteCache2;

    public TranscationOptimisticScript() {
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,TranscationCache1> cacheConfiguration1 = new TranscationCache1Configuration().getCacheConfiguration() ;
        CacheConfiguration<String,TranscationCache2> cacheConfiguration2 = new TranscationCache2Configuration().getCacheConfiguration() ;
        ignite.destroyCache(cacheConfiguration1.getName());
        ignite.destroyCache(cacheConfiguration2.getName());
        igniteCache1 = ignite.createCache(cacheConfiguration1);
        igniteCache2 = ignite.createCache(cacheConfiguration2);

    }

    public void start() {
        Random random = new Random() ;
        boolean bo = random.nextBoolean() ;
        System.out.println("-----------标识位："+bo);
        TranscationCache1 transcationCache1 = new TranscationCache1("1",1) ;
        TranscationCache2 transcationCache2 = new TranscationCache2("1",1) ;
        IgniteTransactions transactions = ignite.transactions();
        Transaction tx = transactions.txStart(TransactionConcurrency.OPTIMISTIC,
                TransactionIsolation.SERIALIZABLE, 1000, 0);

        try {
            igniteCache1.put(transcationCache1.getId(),transcationCache1);
            if (bo){
                throw new Exception("");
            }
            igniteCache2.put(transcationCache2.getId(),transcationCache2);
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
        TranscationOptimisticScript scirpt = new TranscationOptimisticScript();
        scirpt.start();
    }

}
