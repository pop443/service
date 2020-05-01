package com.newland.ignite.cachestore.test;


import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.cachestore.entity.ExpiryConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.transactions.TransactionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.util.*;

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
    public void getAll() {
        Set<String> set = new HashSet<>() ;
        set.add("1");
        set.add("3");
        set.add("2");
        set.add("4");
        set.add("5");
        Map<String,Expiry> map = igniteCache.getAll(set);
        map.forEach((s, expiry) -> System.out.println(expiry));
    }

    @Test
    public void get() {
        Expiry expiry = igniteCache.get("1");
        System.out.println(expiry);
    }

    @Test
    public void put() {
        try {
            for (int i = 0; i < 10; i++) {
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

    @Test
    public void EP() {
        try {
            for (int i = 0; i < 5; i++) {
                String key = i + "";
                Expiry expiry = new Expiry(key, key, key, new Automation(key, i, key));
                IgniteCache<String, BinaryObject> ic = igniteCache.withKeepBinary() ;
                ic.invoke(key, new CacheEntryProcessor<String, BinaryObject, BinaryObject>() {
                    @Override
                    public BinaryObject process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
                        mutableEntry.setValue((BinaryObject)objects[0]);
                        return null;
                    }
                },IgniteUtil.toBinary(expiry));

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

    public static void main(String[] args) {
        try {
            Ignite ignite = IgniteUtil.getIgnite() ;
            ExpiryConfiguration cfg = new ExpiryConfiguration() ;
            IgniteCache<String, Expiry> igniteCache = cfg.getIgniteCache(ignite);
            IgniteCache<String, BinaryObject> ic = igniteCache.withKeepBinary() ;
            Timer timer = new Timer("Stable") ;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 5; i++) {
                            String key = i + "";
                            Expiry expiry = new Expiry(key, key, key, new Automation(key, i, key));

                            ic.invoke(key, new CacheEntryProcessor<String, BinaryObject, BinaryObject>() {
                                @Override
                                public BinaryObject process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
                                    mutableEntry.setValue((BinaryObject)objects[0]);
                                    return null;
                                }
                            },IgniteUtil.toBinary(expiry));

                        }
                    } catch (TransactionException e) {
                        e.printStackTrace();
                    }
                }
            }, 0,30*1000);

        } catch (TransactionException e) {
            e.printStackTrace();
            System.out.println(1);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(2);
        }


    }

}
