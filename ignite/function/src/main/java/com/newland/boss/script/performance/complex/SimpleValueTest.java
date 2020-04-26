package com.newland.boss.script.performance.complex;

import com.newland.boss.entity.performance.complex.SimpleConfiguration;
import com.newland.boss.entity.performance.complex.SimpleValue;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xz on 2020/4/24.
 */
public class SimpleValueTest {
    private Ignite ignite ;
    private IgniteCache<String,SimpleValue> igniteCache ;
    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        SimpleConfiguration cfg = new SimpleConfiguration() ;
        igniteCache = ignite.cache(cfg.getCacheName()) ;
    }

    @Test
    public void test1(){
        SimpleValue simpleValue = igniteCache.get("1") ;
        System.out.println(simpleValue);
    }

    @After
    public void after(){
        if (ignite!=null){
            ignite.close();
        }
    }
}
