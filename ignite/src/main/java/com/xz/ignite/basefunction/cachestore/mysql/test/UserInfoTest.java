package com.xz.ignite.basefunction.cachestore.mysql.test;

import com.xz.ignite.basefunction.cachestore.entity.UserInfo;
import com.xz.ignite.basefunction.cachestore.mysql.custcachestore.sessionlisten.DruidCacheStoreSessionListen;
import com.xz.ignite.basefunction.cachestore.mysql.custcachestore.UserInfoCacheStore;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.configuration.FactoryBuilder;
import java.util.*;

/**
 * Created by xz on 2020/2/8.
 */
public class UserInfoTest {
    private String cacheName = null ;
    private Ignite ignite = null ;
    private IgniteCache<String,UserInfo> igniteCache = null ;

    @Before
    public void before(){
        cacheName = UserInfo.class.getSimpleName().toUpperCase() ;
        ignite = IgniteUtil.getIgniteByXml() ;
        //ignite.destroyCache(cacheName);
        igniteCache = ignite.cache(cacheName) ;
        if (igniteCache==null){
            igniteCache = create(ignite,cacheName) ;
        }
        System.out.println("end before");
    }

    @Test
    public void destroyCache(){
        ignite.destroyCache(cacheName);
    }


    @Test
    public void loadCache(){
        igniteCache.loadCache(new IgniteBiPredicate<String, UserInfo>() {
            @Override
            public boolean apply(String s, UserInfo userInfo) {
                if (s.equals("1")||s.equals("2")||s.equals("3")||s.equals("4")){
                    return true ;
                }
                return false;
            }
        }, 10);
    }
    @Test
    public void get(){
        for (int i = 0; i < 20; i++) {
            UserInfo userinfo = igniteCache.get(i+"") ;
            System.out.println(userinfo);
        }

    }
    @Test
    public void getAll(){
        Set<String> list = new HashSet<>() ;
        for (int i = 690; i < 710; i++) {
            list.add(i+"");
        }
        Map<String,UserInfo> result = igniteCache.getAll(list) ;
        result.forEach((k,v)->{
            System.out.println(v);
        });
    }
    @Test
    public void put(){
        String key ="6" ;
        UserInfo userinfo = new UserInfo(key,key,key,key);
        try {
            igniteCache.put(key,userinfo);
        } catch (Exception e) {
            System.out.println(e.getClass()+"--"+e.getMessage());
        }
    }
    @Test
    public void putMany(){
        for (int i = 0; i < 500; i++) {
            String key =i+"" ;
            UserInfo userinfo = new UserInfo(key,key,key,key);
            igniteCache.put(key,userinfo);
            System.out.println(key);
        }

    }

    @Test
    public void putAll(){
        Map<String,UserInfo> map = new HashMap<>() ;
        for (int i = 800; i < 900; i++) {
            String key =i+"" ;
            UserInfo userinfo = new UserInfo(key,key,key,key);
            map.put(key,userinfo) ;
        }
        igniteCache.putAll(map);
    }

    /**
     * 删除ignite缓存
     * 调用cachestore delete 删除第三方持久化
     */
    @Test
    public void remove(){
        igniteCache.remove("4");
        UserInfo userinfo = igniteCache.get("4") ;
        System.out.println(userinfo);
    }
    /**
     * 删除ignite缓存
     */
    @Test
    public void clear(){
        igniteCache.clear("3");
        UserInfo userinfo = igniteCache.get("3") ;
        System.out.println(userinfo);
    }

    @Test
    public void clearAll(){
        igniteCache.clear();
    }

    @Test
    public void datastream(){
        IgniteDataStreamer<String, UserInfo> stmr = ignite.dataStreamer(cacheName);
        Map<String,UserInfo> map = new HashMap<>() ;
        for (int i = 0; i < 100; i++) {
            String key =i+"" ;
            UserInfo userinfo = new UserInfo(key,key,key,key);
            map.put(key,userinfo) ;
        }
        stmr.addData(map);
        stmr.flush();
        stmr.close();
    }


    @After
    public void after(){
        System.out.println("--after");
        igniteCache.close();
        IgniteUtil.release(ignite);
    }


    public static IgniteCache<String,UserInfo> create(Ignite ignite,String cacheName){
        System.out.println("----create UserInfo");
        CacheConfiguration<String,UserInfo> cacheConfiguration = new CacheConfiguration<>() ;
        cacheConfiguration.setName(cacheName) ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        //备份数量
        cacheConfiguration.setBackups(1) ;
        cacheConfiguration.setSqlSchema("cachestore") ;
        cacheConfiguration.setIndexedTypes( String.class ,UserInfo.class ) ;
        //默认为异步的再平衡
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC) ;
        cacheConfiguration.setCacheStoreFactory(FactoryBuilder.factoryOf(UserInfoCacheStore.class));
        cacheConfiguration.setCacheStoreSessionListenerFactories(FactoryBuilder.factoryOf(DruidCacheStoreSessionListen.class));
        cacheConfiguration.setReadThrough(true);
        cacheConfiguration.setWriteThrough(true);
        //后写
        //cacheConfiguration.setWriteBehindEnabled(true);

        //cacheConfiguration.setWriteBehindFlushSize(5120);
        //cacheConfiguration.setWriteBehindFlushFrequency(5000);

        IgniteCache<String,UserInfo> igniteCache = ignite.createCache(cacheConfiguration) ;
        igniteCache.enableStatistics(true);
        return igniteCache ;
    }
}
