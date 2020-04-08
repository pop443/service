package com.newland.ignite.cachestore.test;


import com.newland.ignite.cachestore.TrueIgniteBiInClosure;
import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.cachestore.entity.ExpiryConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by xz on 2020/2/9.
 */
public class ExpiryTest {
    private ExpiryConfiguration cfg = null;
    private Ignite ignite = null;
    private IgniteCache<String, Expiry> igniteCache = null;

    @Before
    public void before() {
        cfg = new ExpiryConfiguration();
        ignite = IgniteUtil.getIgnite() ;
        //ignite.destroyCache(cacheName);
        igniteCache = cfg.getIgniteCache(ignite);
    }

    @Test
    public void create() {
    }

    @Test
    public void destroyCache() {
        ignite.destroyCache(cfg.getCacheName());
    }

    @Test
    public void load() {
        igniteCache.loadCache(null,8);
    }

    @Test
    public void get() {
        Set<String> set = new HashSet<>() ;
        set.add("1");
        set.add("3");
        set.add("7");
        set.add("4");
        set.add("6");
        Map<String,Expiry> map = igniteCache.getAll(set);
        map.forEach((s, expiry) -> System.out.println(expiry));
    }

    @Test
    public void put() {
        try {
            for (int i = 0; i < 50; i++) {
                String key = i + "";
                Expiry expiry = new Expiry(key, key, key, new Automation(key, i, key));

                igniteCache.put(key, expiry);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Course result = igniteCache.get(key) ;
        //System.out.println(result);
    }

    /**
     * 删除ignite缓存
     * 调用cachestore delete 删除第三方持久化
     */
    @Test
    public void remove() {
        String key = "6";
        igniteCache.remove(key);
    }

    /**
     * 删除ignite缓存
     */
    @Test
    public void clear() {
        igniteCache.clear("3");
        Expiry expiry = igniteCache.get("3");
        System.out.println(expiry);
    }

    @Test
    public void clearAll() {
        igniteCache.clear();
    }


    @After
    public void after() {
        cfg.close();
        if (ignite != null) {
            IgniteUtil.release(ignite);
        }
    }

}
