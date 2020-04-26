package com.newland.boss.script.performance.complex;

import com.newland.boss.entity.performance.complex.*;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xz on 2020/4/24.
 */
public class ComplexValueTest {
    private Ignite ignite;
    private IgniteCache<ComplexKey, ComplexValue> igniteCache;

    @Before
    public void before() {
        ignite = IgniteUtil.getIgnite();
        ComplexConfiguration cfg = new ComplexConfiguration();
        igniteCache = ignite.getOrCreateCache(cfg.getCacheConfiguration());
    }

    @Test
    public void test1() {
        int key = 2 ;
        ComplexKey complexKey = new ComplexKey(key+"", 1L, 1L, "1", "1");
        ComplexValue complexValue = igniteCache.get(complexKey);
        System.out.println(complexValue);
    }

    @Test
    public void test2() {
        int key = 3 ;
        ComplexKey complexKey = new ComplexKey(key+"", 1L, 1L, "1", "1");
        ComplexValue complexValue = new ComplexValue("1", "1");
        igniteCache.put(complexKey, complexValue);
        System.out.println(complexValue);
    }

    @After
    public void after() {
        if (ignite != null) {
            ignite.close();
        }
    }
}
