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

import java.util.Random;

/**
 * 全局锁
 */
public class TranscationLockScript {
    private Ignite ignite ;
    private IgniteCache<String,TranscationCache1> igniteCache1;
    private IgniteCache<String,TranscationCache2> igniteCache2;

    public TranscationLockScript() {
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
        bo = true ;
        System.out.println("-----------标识位："+bo);
        TranscationCache1 transcationCache1 = new TranscationCache1("1","1") ;
        TranscationCache2 transcationCache2 = new TranscationCache2("1","1") ;
        IgniteLock lock = ignite.reentrantLock("test",false,false,true) ;
        lock.lock();
        try {
            igniteCache1.put(transcationCache1.getId(),transcationCache1);
            igniteCache2.put(transcationCache2.getId(),transcationCache2);
            if (bo){
                throw new Exception("");
            }
            System.out.println("------执行程序 正常提交-----");
        } catch (Exception e) {
            System.out.println("------中断程序 回滚-----");
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        igniteCache1.close();
        igniteCache2.close();
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        //System.setProperty(IgniteSystemProperties.IGNITE_FORCE_MVCC_MODE_IN_TESTS, "true");
        System.out.println("---lock---");
        TranscationLockScript scirpt = new TranscationLockScript();
        scirpt.start();
    }

}
