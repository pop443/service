package com.newland.ignite.cachestore.test;

import com.newland.ignite.cachestore.entity.*;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.transactions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xz on 2020/2/9.
 */
public class TranscationTest {
    private UserInfoConfiguration userinfocfg = null;
    private CourseConfiguration coursecfg = null;
    private ExpiryConfiguration expirycfg = null;
    private Ignite ignite = null;
    private IgniteCache<String, UserInfo> userinfoIgniteCache = null;
    private IgniteCache<String, Course> courseIgniteCache = null;
    private IgniteCache<String, Expiry> expiryIgniteCache = null;

    @Before
    public void before() {
        userinfocfg = new UserInfoConfiguration();
        coursecfg = new CourseConfiguration();
        expirycfg = new ExpiryConfiguration();
        ignite = IgniteUtil.getIgniteByXml("cachestore/cachesotore.xml");
        userinfoIgniteCache = userinfocfg.getIgniteCache(ignite);
        courseIgniteCache = coursecfg.getIgniteCache(ignite);
        expiryIgniteCache = expirycfg.getIgniteCache(ignite);
    }

    @Test
    public void transaction() {
        String key = "1";
        IgniteTransactions transactions = ignite.transactions();
        Transaction tx = transactions.txStart(TransactionConcurrency.OPTIMISTIC,
                TransactionIsolation.REPEATABLE_READ, 300, 0);
        try {
            courseIgniteCache.put(key, new Course(key, key, key));
            userinfoIgniteCache.put(key, new UserInfo(key, key, key, key));
            tx.commit();
        } catch (TransactionException e) {
            System.out.println("TransactionException " + e.getClass());
            if (e.getCause() instanceof TransactionTimeoutException && e.getCause().getCause() instanceof TransactionDeadlockException) {
                System.out.println(e.getCause().getCause().getMessage());
            }
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.rollback();
            tx.close();
        }

    }

    @Test
    public void transaction2() {
        String key = "1";
        IgniteTransactions transactions = ignite.transactions();
        Transaction tx = transactions.txStart(TransactionConcurrency.OPTIMISTIC,
                TransactionIsolation.REPEATABLE_READ, 300, 0);
        try {
            expiryIgniteCache.put(key, new Expiry(key, key, key, new Automation()));
            courseIgniteCache.put(key, new Course(key, key, key));
            tx.commit();
        } catch (TransactionException e) {
            System.out.println("TransactionException " + e.getClass());
            if (e.getCause() instanceof TransactionTimeoutException && e.getCause().getCause() instanceof TransactionDeadlockException) {
                System.out.println(e.getCause().getCause().getMessage());
            }
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.rollback();
            tx.close();
        }

    }

    @After
    public void after() {
        userinfoIgniteCache.close();
        courseIgniteCache.close();
        expiryIgniteCache.close();

        if (ignite != null) {
            IgniteUtil.release(ignite);
        }
    }
}
