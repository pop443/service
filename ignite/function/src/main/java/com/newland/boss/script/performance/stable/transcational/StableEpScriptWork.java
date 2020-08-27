package com.newland.boss.script.performance.stable.transcational;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.transcation.*;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.GetEp1;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.label.utils.IdGen;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionException;
import org.apache.ignite.transactions.TransactionIsolation;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by xz on 2020/3/10.
 */
public class StableEpScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String, BinaryObject> igniteCache1;
    private IgniteCache<String, BinaryObject> igniteCache2;
    private IgniteCache<String, BinaryObject> igniteCache3;
    private IgniteCache<String, BinaryObject> igniteCache4;
    private IgniteCache<String, BinaryObject> igniteCache5;
    private Ignite ignite;
    private Integer baseKey;
    private AtomicLong atomicLong;
    private Timer timer;

    public StableEpScriptWork(EnterParam enterParam, Ignite ignite, List<IgniteCache> list, Integer baseKey) {
        this.enterParam = enterParam;
        this.igniteCache1 = list.get(0).withKeepBinary();
        this.igniteCache2 = list.get(1).withKeepBinary();
        this.igniteCache3 = list.get(2).withKeepBinary();
        this.igniteCache4 = list.get(3).withKeepBinary();
        this.igniteCache5 = list.get(4).withKeepBinary();
        this.baseKey = baseKey;
        this.ignite = ignite;
        atomicLong = new AtomicLong(0);
        timer = new Timer("Stable");
    }

    @Override
    public Long call() throws Exception {
        System.out.println("start-------------------");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Long count = atomicLong.getAndSet(0);
                System.out.println(Thread.currentThread().getName() + "循环次数：" + count + ",置0");
            }
        }, 10 * 60 * 1000, 10 * 60 * 1000);
        return working();
    }

    private Long working() {
        Long l1 = System.currentTimeMillis();
        IgniteTransactions igniteTransactions = ignite.transactions();
        while ((System.currentTimeMillis() - l1) < 3 * 60 * 60 * 1000) {
            //while (System.currentTimeMillis() - l1 < 10*1000) {
            atomicLong.addAndGet(1);
            try {
                CustObjBuild<TranscationCache1> build1 = new CustObjBuild<>(TranscationCache1.class);
                CustObjBuild<TranscationCache2> build2 = new CustObjBuild<>(TranscationCache2.class);
                CustObjBuild<TranscationCache3> build3 = new CustObjBuild<>(TranscationCache3.class);
                CustObjBuild<TranscationCache4> build4 = new CustObjBuild<>(TranscationCache4.class);
                CustObjBuild<TranscationCache5> build5 = new CustObjBuild<>(TranscationCache5.class);
                String randomKey = IdGen.uuid();
                TranscationCache1 obj1 = build1.build4k(randomKey + "");
                TranscationCache2 obj2 = build2.build4k(randomKey + "");
                TranscationCache3 obj3 = build3.build4k(randomKey + "");
                TranscationCache4 obj4 = build4.build4k(randomKey + "");
                TranscationCache5 obj5 = build5.build4k(randomKey + "");

                Transaction tx = igniteTransactions.txStart(TransactionConcurrency.OPTIMISTIC,
                        TransactionIsolation.SERIALIZABLE, 5000, 5);
                try {
                    igniteCache1.invoke(obj1.getId(), new PutEp1(), IgniteUtil.toBinary(obj1));
                    igniteCache2.invoke(obj2.getId(), new PutEp1(), IgniteUtil.toBinary(obj2));
                    igniteCache3.invoke(obj3.getId(), new PutEp1(), IgniteUtil.toBinary(obj3));
                    igniteCache4.invoke(obj4.getId(), new PutEp1(), IgniteUtil.toBinary(obj4));
                    igniteCache5.invoke(obj5.getId(), new PutEp1(), IgniteUtil.toBinary(obj5));
                    tx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    tx.rollback();
                    tx.close();
                }
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }
        timer.cancel();
        timer = null;
        return 0L;
    }
}
