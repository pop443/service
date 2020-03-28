package com.newland.boss.script.features.cachestore;

import com.newland.ignite.cachestore.entity.Course;
import com.newland.ignite.cachestore.entity.CourseConfiguration;
import com.newland.ignite.cachestore.entity.UserInfo;
import com.newland.ignite.cachestore.entity.UserInfoConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;

import java.util.Random;

/**
 * Created by xz on 2020/3/28.
 */
public class CacheStoreTranscationScript {
    private Ignite ignite ;
    private IgniteCache<String,UserInfo> igniteCache1;
    private IgniteCache<String,Course> igniteCache2;

    public CacheStoreTranscationScript() {
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,UserInfo> cacheConfiguration1 = new UserInfoConfiguration().getCacheConfiguration() ;
        CacheConfiguration<String,Course> cacheConfiguration2 = new CourseConfiguration().getCacheConfiguration() ;
        ignite.destroyCache(cacheConfiguration1.getName());
        ignite.destroyCache(cacheConfiguration2.getName());
        igniteCache1 = ignite.createCache(cacheConfiguration1);
        igniteCache2 = ignite.createCache(cacheConfiguration2);

    }

    public void start() {
        Random random = new Random() ;
        boolean bo = random.nextBoolean() ;
        UserInfo userInfo = new UserInfo("1","1","1","1") ;
        Course course = new Course("1","1","1") ;
        IgniteTransactions transactions = ignite.transactions();
        Transaction tx = transactions.txStart();
        try {
            igniteCache1.put(userInfo.getId(),userInfo);
            if (!bo){
                throw new Exception("");
            }
            igniteCache2.put(course.getId(),course);
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
        System.out.println("---cachestore 事务---");
        CacheStoreTranscationScript scirpt = new CacheStoreTranscationScript();
        scirpt.start();
    }
}
