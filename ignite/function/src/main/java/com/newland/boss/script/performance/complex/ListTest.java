package com.newland.boss.script.performance.complex;

import com.newland.boss.entity.performance.complex.SimpleConfiguration;
import com.newland.boss.entity.performance.complex.SimpleValue;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/4/24.
 */
public class ListTest {
    private Ignite ignite ;
    private IgniteCache<String,List<String>> igniteCache ;
    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,List<String>> cfg = new CacheConfiguration<>() ;
        cfg.setIndexedTypes(String.class,List.class) ;
        cfg.setName("List");
        cfg.setSqlSchema("NEWLAND");
        igniteCache = ignite.getOrCreateCache(cfg) ;
    }

    @Test
    public void test1(){
        String key = "1" ;
        List<String> list = igniteCache.get(key) ;
        list.forEach(System.out::println);
    }
    @Test
    public void test2(){
        String key = "1" ;
        List<String> list = new ArrayList<>() ;
        list.add(key) ;
        list.add(key+1) ;
        igniteCache.put(key,list);
    }

    @After
    public void after(){
        if (ignite!=null){
            ignite.close();
        }
    }
}
