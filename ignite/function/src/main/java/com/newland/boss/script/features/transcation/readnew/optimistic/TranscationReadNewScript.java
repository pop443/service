package com.newland.boss.script.features.transcation.readnew.optimistic;

import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache1Configuration;
import com.newland.boss.entity.transcation.TranscationCache2;
import com.newland.boss.entity.transcation.TranscationCache2Configuration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;

import java.util.concurrent.CountDownLatch;

/**
 * step1 初始化一个缓存
 * step2 开启事务
 * step3 更新一个缓存
 * step4 提交事务
 * 事务过程是否能读到最新数据
 */
public class TranscationReadNewScript {
    private Ignite ignite ;
    private IgniteCache<String,TranscationCache1> igniteCache1;
    private IgniteCache<String,TranscationCache2> igniteCache2;
    private CountDownLatch countDownLatch1 ;
    private CountDownLatch countDownLatch2 ;
    private CountDownLatch countDownLatch3 ;
    private CountDownLatch countDownLatch4 ;

    public TranscationReadNewScript() {
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,TranscationCache1> cacheConfiguration1 = new TranscationCache1Configuration().getCacheConfiguration() ;
        CacheConfiguration<String,TranscationCache2> cacheConfiguration2 = new TranscationCache2Configuration().getCacheConfiguration() ;
        igniteCache1 = ignite.getOrCreateCache(cacheConfiguration1);
        igniteCache2 = ignite.getOrCreateCache(cacheConfiguration2);
        countDownLatch1 = new CountDownLatch(1) ;
        countDownLatch2 = new CountDownLatch(1) ;
        countDownLatch3 = new CountDownLatch(1) ;
        countDownLatch4 = new CountDownLatch(1) ;

    }

    public void start() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //初始化 step1
                    TranscationCache2 transcationCache21 = new TranscationCache2("1","1") ;
                    igniteCache2.put(transcationCache21.getId(),transcationCache21);
                    countDownLatch1.countDown();
                    countDownLatch2.await();
                    TranscationCache2 transcationCache22 = new TranscationCache2("1","17") ;
                    igniteCache2.put(transcationCache22.getId(),transcationCache22);
                    System.out.println("修改 transcationCache22 17");
                    countDownLatch3.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }) ;
        thread.start();
        TranscationCache1 transcationCache1 = new TranscationCache1("1","1") ;
        IgniteTransactions transactions = ignite.transactions();
        countDownLatch1.await();

        Transaction tx = transactions.txStart(TransactionConcurrency.OPTIMISTIC,
                TransactionIsolation.SERIALIZABLE, 10000, 0);
        try {
            igniteCache1.put(transcationCache1.getId(),transcationCache1);
            TranscationCache2 transcationCache2 = igniteCache2.get("1") ;
            System.out.println("读 transcationCache22 "+transcationCache2.getS01());
            countDownLatch2.countDown();
            transcationCache2.setS01(transcationCache2.getS01()+"--");
            igniteCache2.put(transcationCache2.getId(),transcationCache2);
            countDownLatch3.await();
            System.out.println("提交 transcationCache22 "+transcationCache2.getS01());
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            tx.rollback();
            tx.close();
        }
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        TranscationReadNewScript scirpt = new TranscationReadNewScript();
        scirpt.start();
    }

}
