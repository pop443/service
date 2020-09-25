package com.newland.boss.script.features.transcation.readnew.pessimistic;

import com.newland.boss.entity.performance.complex.ComplexList;
import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache1Configuration;
import com.newland.boss.entity.transcation.TranscationCache2;
import com.newland.boss.entity.transcation.TranscationCache2Configuration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;

import javax.cache.Cache;
import java.util.Iterator;
import java.util.List;
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
    private CountDownLatch countDownLatch1 ;
    private CountDownLatch countDownLatch2 ;

    public TranscationReadNewScript() {
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,TranscationCache1> cacheConfiguration1 = new TranscationCache1Configuration().getCacheConfiguration() ;
        ignite.destroyCache(cacheConfiguration1.getName());
        igniteCache1 = ignite.getOrCreateCache(cacheConfiguration1);
        countDownLatch1 = new CountDownLatch(1) ;
        countDownLatch2 = new CountDownLatch(1) ;
    }

    public void start() throws InterruptedException {
        igniteCache1.put("1",new TranscationCache1("1","1"));
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch1.await();
                    System.out.println("x1");
                    igniteCache1.get("1");
                    System.out.println("x2");
                    igniteCache1.put("1",new TranscationCache1("2","2"));
                    System.out.println("x3");
                    igniteCache1.put("1",new TranscationCache1("3","3"));
                    countDownLatch2.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }) ;
        thread.start();

        IgniteTransactions transactions = ignite.transactions();
        Transaction tx = transactions.txStart(TransactionConcurrency.PESSIMISTIC,
                TransactionIsolation.REPEATABLE_READ, 10000, 2);
        try {
            TranscationCache1 transcationCache1 = Scan("1");
            System.out.println("y1");
            countDownLatch1.countDown();
            countDownLatch2.await();
            System.out.println("y2");
            transcationCache1.setId("6");
            igniteCache1.put("1",transcationCache1);
            System.out.println("y3");
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

    private TranscationCache1 get(String key){
        return igniteCache1.get("1") ;
    }

    private TranscationCache1 getSql(String key){
        SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery("select _val from NEWLAND.TRANSCATIONCACHE1 where _key='1'") ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache1.query(sqlFieldsQuery) ;
        return (TranscationCache1)fieldsQueryCursor.getAll().get(0).get(0) ;
    }

    private TranscationCache1 Scan(String key){
        ScanQuery<String,BinaryObject> scanQuery = new ScanQuery<>(new IgniteBiPredicate<String,BinaryObject>() {
            @Override
            public boolean apply(String s, BinaryObject binaryObject) {
                return s.equals(key);
            }
        }) ;
        IgniteCache<String,BinaryObject> ic = igniteCache1.withKeepBinary() ;
        QueryCursor<Cache.Entry<String,BinaryObject>> cursor = ic.query(scanQuery) ;
        return cursor.getAll().get(0).getValue().deserialize() ;
    }

}
