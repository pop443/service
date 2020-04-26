package com.newland.boss.script.performance.complex;

import com.newland.boss.entity.performance.complex.ComplexList;
import com.newland.boss.entity.performance.complex.ComplexListConfiguration;
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
public class ComplexListTest {
    private Ignite ignite ;
    private IgniteCache<String,ComplexList> igniteCache ;
    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,ComplexList> cfg = new ComplexListConfiguration().getCacheConfiguration();
        igniteCache = ignite.getOrCreateCache(cfg) ;
    }

    @Test
    public void test1(){
        String key = "1" ;
        ComplexList list = igniteCache.get(key) ;
        System.out.println(list);
    }
    @Test
    public void test2(){
        int key = 1 ;
        ComplexList complexList = new ComplexList(1) ;
        igniteCache.put(key+"",complexList);
    }

    @After
    public void after(){
        if (ignite!=null){
            ignite.close();
        }
    }
}
