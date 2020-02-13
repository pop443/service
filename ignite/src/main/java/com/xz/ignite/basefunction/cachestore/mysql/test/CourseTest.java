package com.xz.ignite.basefunction.cachestore.mysql.test;

import com.xz.ignite.basefunction.cachestore.entity.Course;
import com.xz.ignite.basefunction.cachestore.mysql.config.MysqlConnection;
import com.xz.ignite.basefunction.cachestore.mysql.custcachestore.CourseCacheStore;
import com.xz.ignite.basefunction.cachestore.mysql.custcachestore.sessionlisten.JdbcCacheStoreSessionListen;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.configuration.FactoryBuilder;

/**
 * Created by xz on 2020/2/9.
 */
public class CourseTest {
    private String cacheName = null ;
    private Ignite ignite = null ;
    private IgniteCache<String,Course> igniteCache = null ;

    @Before
    public void before(){
        cacheName = Course.class.getSimpleName().toUpperCase() ;
        ignite = IgniteUtil.getIgnite() ;
        igniteCache = ignite.cache(cacheName) ;
        if (igniteCache==null){
            igniteCache = create(ignite,cacheName) ;
        }
    }
    @Test
    public void get(){
        Course course = igniteCache.get("30") ;
        System.out.println(course);
    }
    @Test
    public void put(){
        String key ="30" ;
        Course course = new Course(key,key,key);
        try {
            igniteCache.put(key,course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----put-----");
        //Course result = igniteCache.get(key) ;
        //System.out.println(result);
    }

    /**
     * 删除ignite缓存
     * 调用cachestore delete 删除第三方持久化
     */
    @Test
    public void remove(){
        String key = "6" ;
        igniteCache.remove(key);
    }
    /**
     * 删除ignite缓存
     */
    @Test
    public void clear(){
        igniteCache.clear("3");
        Course course = igniteCache.get("3") ;
        System.out.println(course);
    }

    @Test
    public void clearAll(){
        igniteCache.clear();
    }


    @After
    public void after(){
        if (igniteCache!=null){

        }
        if (ignite!=null){
            IgniteUtil.release(ignite);
        }
    }


    public static IgniteCache<String,Course> create(Ignite ignite,String cacheName){
        CacheConfiguration<String,Course> cacheConfiguration = new CacheConfiguration<>() ;
        cacheConfiguration.setName(cacheName) ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        //备份数量
        cacheConfiguration.setBackups(1) ;
        cacheConfiguration.setSqlSchema("cachestore") ;
        cacheConfiguration.setIndexedTypes( String.class ,Course.class ) ;
        //默认为异步的再平衡
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC) ;
        cacheConfiguration.setCacheStoreFactory(FactoryBuilder.factoryOf(CourseCacheStore.class));

        //session listen
        JdbcCacheStoreSessionListen jdbcCacheStoreListen = new JdbcCacheStoreSessionListen() ;
        jdbcCacheStoreListen.setDataSource(MysqlConnection.getDataSource());
        cacheConfiguration.setCacheStoreSessionListenerFactories(FactoryBuilder.factoryOf(jdbcCacheStoreListen)) ;



        cacheConfiguration.setReadThrough(true);
        cacheConfiguration.setWriteThrough(true);

        IgniteCache<String,Course> igniteCache = ignite.createCache(cacheConfiguration) ;
        return igniteCache ;
    }
}
