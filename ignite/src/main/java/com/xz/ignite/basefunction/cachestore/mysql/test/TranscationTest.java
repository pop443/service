package com.xz.ignite.basefunction.cachestore.mysql.test;

import com.xz.ignite.basefunction.cachestore.entity.Course;
import com.xz.ignite.basefunction.cachestore.entity.Expiry;
import com.xz.ignite.basefunction.cachestore.entity.UserInfo;
import com.xz.ignite.utils.IgniteUtil;
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
    private String userinfoCacheName = null ;
    private String courseCacheName = null ;
    private String expiryCacheName = null ;
    private Ignite ignite = null ;
    private IgniteCache<String,UserInfo> userinfoIgniteCache = null ;
    private IgniteCache<String,Course> courseIgniteCache = null ;
    private IgniteCache<String,Expiry> expiryIgniteCache = null ;

    @Before
    public void before(){
        userinfoCacheName = UserInfo.class.getSimpleName().toUpperCase() ;
        courseCacheName = Course.class.getSimpleName().toUpperCase() ;
        expiryCacheName = Expiry.class.getSimpleName().toUpperCase() ;
        ignite = IgniteUtil.getIgniteByXml() ;
        userinfoIgniteCache = ignite.cache(userinfoCacheName) ;
        if (userinfoIgniteCache==null){
            userinfoIgniteCache = UserInfoTest.create(ignite,userinfoCacheName) ;
        }

        courseIgniteCache = ignite.cache(courseCacheName) ;
        if (courseIgniteCache==null){
            courseIgniteCache = CourseTest.create(ignite,courseCacheName) ;
        }
        expiryIgniteCache = ignite.cache(expiryCacheName) ;
        if (expiryIgniteCache==null){
            expiryIgniteCache = ExpiryTest.create(ignite,expiryCacheName) ;
        }
    }

    @Test
    public void transaction(){
        String key = "1" ;
        IgniteTransactions transactions = ignite.transactions() ;
        Transaction tx = transactions.txStart(TransactionConcurrency.OPTIMISTIC,
                TransactionIsolation.REPEATABLE_READ, 300, 0);
        try {
            courseIgniteCache.put(key,new Course(key,key,key));
            userinfoIgniteCache.put(key,new UserInfo(key,key,key,key));
            tx.commit();
        } catch (TransactionException e) {
            System.out.println("TransactionException "+e.getClass());
            if (e.getCause() instanceof TransactionTimeoutException && e.getCause().getCause() instanceof TransactionDeadlockException){
                System.out.println(e.getCause().getCause().getMessage());
            }
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            tx.rollback();
            tx.close();
        }

    }

    @Test
    public void transaction2(){
        String key = "1" ;
        IgniteTransactions transactions = ignite.transactions() ;
        Transaction tx = transactions.txStart(TransactionConcurrency.OPTIMISTIC,
                TransactionIsolation.REPEATABLE_READ, 300, 0);
        try {
            expiryIgniteCache.put(key,new Expiry(key,key,key));
            courseIgniteCache.put(key,new Course(key,key,key));
            tx.commit();
        } catch (TransactionException e) {
            System.out.println("TransactionException "+e.getClass());
            if (e.getCause() instanceof TransactionTimeoutException && e.getCause().getCause() instanceof TransactionDeadlockException){
                System.out.println(e.getCause().getCause().getMessage());
            }
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            tx.rollback();
            tx.close();
        }

    }

    @After
    public void after(){
        if (userinfoIgniteCache!=null){
            userinfoIgniteCache.close();
        }
        if (courseIgniteCache!=null){
            courseIgniteCache.close();
        }
        if (ignite!=null){
            IgniteUtil.release(ignite);
        }
    }
}
