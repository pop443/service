package com.newland.ignite.cachestore.test;


import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.cachestore.entity.ExpiryConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.lang.IgniteFuture;
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
        ignite = IgniteUtil.getIgnite();
        //ignite.destroyCache(cacheName);
        igniteCache = cfg.getIgniteCache(ignite);

    }

    @Test
    public void create() {
        ignite.createCache(cfg.getCacheConfiguration());
    }

    @Test
    public void show() {
        ignite.cacheNames().forEach(System.out::println);
    }

    @Test
    public void destroyCache() {
        ignite.destroyCache(cfg.getCacheName());
    }

    @Test
    public void load() {
        igniteCache.loadCache(null, 8);
    }

    @Test
    public void getAll() {
        Set<String> set = new HashSet<>();
        set.add("4");
        set.add("3");
        set.add("2");
        set.add("5");
        set.add("7");
        Map<String, Expiry> map = igniteCache.getAll(set);
        map.forEach((s, expiry) -> System.out.println(expiry));
    }

    @Test
    public void get() {
        Expiry expiry = igniteCache.get("2");
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
    public void count() {
        int ALL = igniteCache.size(CachePeekMode.ALL) ;
        int BACKUP = igniteCache.size(CachePeekMode.BACKUP) ;
        int PRIMARY = igniteCache.size(CachePeekMode.PRIMARY) ;
        int NEAR = igniteCache.size(CachePeekMode.NEAR) ;
        int ONHEAP = igniteCache.size(CachePeekMode.ONHEAP) ;
        int OFFHEAP = igniteCache.size(CachePeekMode.OFFHEAP) ;
        StringBuilder sb = new StringBuilder();
        sb.append("ALL:").append(ALL).append("\r\n") ;
        sb.append("BACKUP:").append(BACKUP).append("\r\n") ;
        sb.append("PRIMARY:").append(PRIMARY).append("\r\n") ;
        sb.append("NEAR:").append(NEAR).append("\r\n") ;
        sb.append("ONHEAP:").append(ONHEAP).append("\r\n") ;
        sb.append("OFFHEAP:").append(OFFHEAP).append("\r\n") ;
        System.out.println(sb.toString());

    }

    @Test
    public void EP() {

        for (int i = 0; i < 10; i++) {
            try {
                String key = i + "";
                Expiry expiry = igniteCache.invoke(key, new CacheEntryProcessor<String, Expiry, Expiry>() {
                    @Override
                    public Expiry process(MutableEntry<String, Expiry> mutableEntry, Object... objects) throws EntryProcessorException {
                        return mutableEntry.getValue();
                    }
                });
                System.out.println(expiry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void EP2() {

        for (int i = 0; i < 10000; i++) {
            try {
                String key = i + "";
                IgniteFuture<Expiry> future = igniteCache.invokeAsync(key, new CacheEntryProcessor<String, Expiry, Expiry>() {
                    @Override
                    public Expiry process(MutableEntry<String, Expiry> mutableEntry, Object... objects) throws EntryProcessorException {
                        return mutableEntry.getValue();
                    }
                });
                System.out.println(future.get(10));
            } catch (Exception e) {
                System.out.println("time out key:"+i);
            }

        }

    }

    @Test
    public void EP3() {
        int i = 1 ;
        while (true){
            if (i==100){
                i = 1 ;
            }
            try {
                String key = i++ + "";
                IgniteFuture<Expiry> future = igniteCache.invokeAsync(key, new CacheEntryProcessor<String, Expiry, Expiry>() {
                    @Override
                    public Expiry process(MutableEntry<String, Expiry> mutableEntry, Object... objects) throws EntryProcessorException {
                        return mutableEntry.getValue();
                    }
                });
                System.out.println(future.get(10));
            } catch (Exception e) {
                System.out.println("time out key:"+i+" e:"+e.getMessage());
            }

        }

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
            Ignite ignite = IgniteUtil.getIgnite();
            ExpiryConfiguration cfg = new ExpiryConfiguration();
            IgniteCache<String, Expiry> igniteCache = cfg.getIgniteCache(ignite);
            IgniteCache<String, BinaryObject> ic = igniteCache.withKeepBinary();
            Timer timer = new Timer("Stable");
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
                                    mutableEntry.setValue((BinaryObject) objects[0]);
                                    return null;
                                }
                            }, IgniteUtil.toBinary(expiry));

                        }
                    } catch (TransactionException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 30 * 1000);

        } catch (TransactionException e) {
            e.printStackTrace();
            System.out.println(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(2);
        }


    }

}
