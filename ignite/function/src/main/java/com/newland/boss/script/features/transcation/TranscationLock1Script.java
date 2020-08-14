package com.newland.boss.script.features.transcation;

import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache1Configuration;
import com.newland.boss.entity.transcation.TranscationCache2;
import com.newland.boss.entity.transcation.TranscationCache2Configuration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteLock;
import org.apache.ignite.configuration.CacheConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;

/**
 * 缓存锁
 */
public class TranscationLock1Script {
    private Ignite ignite ;
    private IgniteCache<String,TranscationCache1> igniteCache1;

    public TranscationLock1Script() {
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,TranscationCache1> cacheConfiguration1 = new TranscationCache1Configuration().getCacheConfiguration() ;
        igniteCache1 = ignite.getOrCreateCache(cacheConfiguration1);
    }

    public void start() {
        TranscationCache1 transcationCache1 = new TranscationCache1("1","1") ;
        TranscationCache1 transcationCache2 = new TranscationCache1("2","2") ;
        TranscationCache1 transcationCache3 = new TranscationCache1("3","3") ;
        igniteCache1.put(transcationCache1.getId(),transcationCache1);
        List<String> list = new ArrayList<>() ;
        for (int i = 0; i < 10; i++) {
            list.add(i+"") ;
        }
        Lock lock = igniteCache1.lockAll(list) ;
        lock.tryLock();
        try {
            System.out.println("get lock");
            igniteCache1.put(transcationCache2.getId(),transcationCache2);
            igniteCache1.put(transcationCache3.getId(),transcationCache3);
            Thread.sleep(20000);
            System.out.println("------执行程序 正常提交-----");
        } catch (Exception e) {
            System.out.println("------中断程序 回滚-----");
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        igniteCache1.close();
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        //System.setProperty(IgniteSystemProperties.IGNITE_FORCE_MVCC_MODE_IN_TESTS, "true");
        System.out.println("---lock---");
        TranscationLock1Script scirpt = new TranscationLock1Script();
        scirpt.start();
    }

}
