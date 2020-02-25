package com.xz.ignite.basefunction.cachestore.mysql.test;

import com.xz.ignite.basefunction.cachestore.entity.Expiry;
import com.xz.ignite.basefunction.cachestore.mysql.custcachestore.ExpiryCacheStore;
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
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

/**
 * Created by xz on 2020/2/9.
 */
public class ExpiryTest {
    private String cacheName = null;
    private Ignite ignite = null;
    private IgniteCache<String, Expiry> igniteCache = null;

    @Before
    public void before() {
        cacheName = Expiry.class.getSimpleName().toUpperCase();
        ignite = IgniteUtil.getIgniteByXml();
        ignite.destroyCache(cacheName);
        igniteCache = ignite.cache(cacheName);
        if (igniteCache == null) {
            igniteCache = create(ignite, cacheName);
        }
    }

    @Test
    public void get() {
        Expiry expiry = igniteCache.get("31");
        System.out.println(expiry);
    }

    @Test
    public void put() {

        try {
            Thread.sleep(20000L);
            for (int i = 0; i < 200; i++) {
                String key = i + "";
                Expiry expiry = new Expiry(key, key, key);

                igniteCache.put(key, expiry);

                Thread.sleep(1000L);
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
        if (igniteCache != null) {

        }
        if (ignite != null) {
            IgniteUtil.release(ignite);
        }
    }


    public static IgniteCache<String, Expiry> create(Ignite ignite, String cacheName) {
        CacheConfiguration<String, Expiry> cacheConfiguration = new CacheConfiguration<>();
        cacheConfiguration.setName(cacheName);
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        //备份数量
        cacheConfiguration.setBackups(1);
        cacheConfiguration.setSqlSchema("cachestore");
        cacheConfiguration.setIndexedTypes(String.class, Expiry.class);
        //默认为异步的再平衡
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC);

        //cache store
        cacheConfiguration.setCacheStoreFactory(FactoryBuilder.factoryOf(ExpiryCacheStore.class));
        cacheConfiguration.setReadThrough(true);
        cacheConfiguration.setWriteThrough(true);

        //
        cacheConfiguration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_DAY));

        IgniteCache<String, Expiry> igniteCache = ignite.createCache(cacheConfiguration);
        return igniteCache;
    }
}
