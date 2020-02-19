package com.xz.ignite.basefunction.script;

import com.xz.ignite.basefunction.cachestore.entity.Course;
import com.xz.ignite.basefunction.cachestore.entity.UserInfo;
import com.xz.ignite.basefunction.cachestore.mysql.test.CourseTest;
import com.xz.ignite.basefunction.cachestore.mysql.test.UserInfoTest;
import com.xz.ignite.basefunction.entity.GbRoleMon;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.transactions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xz on 2020/2/18.
 */
public class CreateTransaction {
    private Ignite ignite = null ;
    private IgniteCache<String,GbRoleMon> igniteCache = null ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        igniteCache = ignite.cache("GBROLEMON") ;
    }

    @Test
    public void transaction(){
        String key = "15794997226352057c42df8e34ed193112f45d4f779f9" ;
        for (int i = 0; i < 10; i++) {
            int index = i ;
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("--------------");
                    System.out.println("---    "+index+"    ---");
                    System.out.println("--------------");
                    IgniteTransactions transactions = ignite.transactions() ;
                    Transaction tx = transactions.txStart();
                    try {
                        GbRoleMon gbRoleMon = igniteCache.get(key) ;
                        gbRoleMon.setApp_cnt(1);
                        igniteCache.put(key,gbRoleMon);

                        Thread.sleep(60*60*60);
                    } catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        System.out.println("--------------");
                        System.out.println("---    "+index+" end   ---");
                        System.out.println("--------------");
                        tx.rollback();
                        tx.close();
                    }
                }
            });
            thread1.start();
        }
        try {
            Thread.sleep(60*60*60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after(){
        if (ignite!=null){
            IgniteUtil.release(ignite);
        }
    }
}
