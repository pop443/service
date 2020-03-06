package com.newland.ignite.secondary;

import com.newland.ignite.secondary.entity.Student;
import com.newland.ignite.secondary.entity.Wear;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/5.
 */
public class StudentTest {
    private Ignite ignite ;
    private String cacheName ;
    private IgniteCache<String,Student> igniteCache ;
    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite();
        cacheName = Student.class.getSimpleName().toUpperCase() ;
        igniteCache = ignite.cache(cacheName) ;
        if (igniteCache==null){
            igniteCache = ignite.createCache(getCacheConfiguration(String.class,Student.class));
        }
    }
    @Test
    public void destroyCache(){
        ignite.destroyCache(cacheName);
    }

    @Test
    public void input(){

        Map<String,Student> map = new HashMap<>() ;
        for (int i = 0; i < 100; i++) {
            String key = i+"" ;
            Wear wear = new Wear("coat"+key,"pants"+key,"shoe"+key) ;
            Student student = new Student(key,key,i,wear) ;
            map.put(key,student) ;
        }
        IgniteDataStreamer<String,Student> ids = ignite.dataStreamer(cacheName) ;
        ids.keepBinary(true) ;
        ids.addData(map) ;
        ids.flush();

    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }

    private <K,V> CacheConfiguration<K,V> getCacheConfiguration(Class<K> stringClass, Class<V> studentClass) {
        CacheConfiguration<K,V> cacheConfiguration = new CacheConfiguration<>();
        cacheConfiguration.setName(cacheName);
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        cacheConfiguration.setStatisticsEnabled(true) ;
        cacheConfiguration.setSqlSchema("label") ;
        cacheConfiguration.setIndexedTypes( stringClass ,studentClass ) ;
        return cacheConfiguration ;
    }
}
