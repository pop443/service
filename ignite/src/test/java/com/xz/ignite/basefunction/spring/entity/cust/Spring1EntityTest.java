package com.xz.ignite.basefunction.spring.entity.cust;

import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xz on 2020/2/28.
 */
public class Spring1EntityTest {
    private Ignite ignite ;
    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void listCacheName(){
        ignite.cacheNames().forEach(System.out::println);

    }

    @Test
    public void destroyCaches(){
        ignite.destroyCaches(ignite.cacheNames());

    }
    @After
    public void after(){

    }

}