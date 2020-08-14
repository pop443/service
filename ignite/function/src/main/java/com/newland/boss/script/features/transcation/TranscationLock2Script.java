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
import org.apache.ignite.transactions.TransactionException;

import java.util.Random;

/**
 * 乐观锁
 */
public class TranscationLock2Script {
    private Ignite ignite;
    private IgniteCache<String, TranscationCache1> igniteCache1;

    public TranscationLock2Script() {
        ignite = IgniteUtil.getIgnite();
        CacheConfiguration<String, TranscationCache1> cacheConfiguration1 = new TranscationCache1Configuration().getCacheConfiguration();
        igniteCache1 = ignite.getOrCreateCache(cacheConfiguration1);
    }

    public void start() {

        for (int i = 0; i < 1000; i++) {
            try {
                System.out.println(i);
                TranscationCache1 transcationCache1 = new TranscationCache1(i + "", "2");
                igniteCache1.put("1", transcationCache1);
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        //System.setProperty(IgniteSystemProperties.IGNITE_FORCE_MVCC_MODE_IN_TESTS, "true");
        System.out.println("---lock---");
        TranscationLock2Script scirpt = new TranscationLock2Script();
        scirpt.start();
    }

}
