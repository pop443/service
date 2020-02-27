package com.xz.ignite.basefunction.continuousquery;

import com.xz.ignite.basefunction.entity.ListenEntity;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.query.ContinuousQuery;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.Cache;
import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryUpdatedListener;
import javax.cache.event.EventType;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.util.*;

/**
 * Created by xz on 2020/2/26.
 */
public class CacheQueryListenEntity {
    private Ignite ignite = null ;
    private String cacheName = null ;
    private IgniteCache<String,ListenEntity> igniteCache = null ;
    private IgniteDataStreamer<String,ListenEntity> stmr = null ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        cacheName = ListenEntity.class.getSimpleName().toUpperCase() ;
        igniteCache = ignite.cache(cacheName) ;
        if (igniteCache == null){
            igniteCache = ignite.createCache(getCacheConfiguration());
        }
        igniteCache = igniteCache.withExpiryPolicy(new CreatedExpiryPolicy(Duration.ONE_MINUTE)) ;
        stmr = ignite.dataStreamer(cacheName);
    }

    @Test
    public void destroyCache(){
        ignite.destroyCache(cacheName);
    }

    @Test
    public void continuousQuery(){
        ContinuousQuery<Object, Object> qry = new ContinuousQuery<>();
        //初始化查询
        qry.setInitialQuery(new ScanQuery<>(20));
        //本地监听
        qry.setLocalListener(new CacheEntryUpdatedListener<Object, Object>() {
            @Override
            public void onUpdated(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> iterable) throws CacheEntryListenerException {
                for (CacheEntryEvent<? extends Object, ? extends Object> e: iterable){
                    if (e.isOldValueAvailable()){
                        System.out.println(e.getOldValue());
                    }
                    EventType eventType = e.getEventType();
                    switch (eventType){
                        case CREATED:
                            System.out.println("--create");
                            break;
                        case UPDATED:
                            System.out.println("--UPDATED");
                            break;
                        case REMOVED:
                            System.out.println("--REMOVED");
                            break;
                        case EXPIRED:
                            System.out.println("--EXPIRED");
                            break;
                    }
                    System.out.println("key=" + e.getKey() + ", val=" + e.getValue());
                }
            }
        });
        //远程节点过滤
        qry.setRemoteFilterFactory(FactoryBuilder.factoryOf(CustCacheEntryEventFilter.class));
        igniteCache.query(qry) ;

        while (true){
            try {
                Thread.sleep(20000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    @Test
    public void allTest(){
        ListenEntity listenEntity = new ListenEntity("11","11") ;
        igniteCache.put("11",listenEntity);
        listenEntity.setAge("111");
        igniteCache.put("11",listenEntity);
        igniteCache.remove("11");

        Map<String,ListenEntity> map = new HashMap<>() ;
        for (int i = 0; i < 10; i++) {
            String key = i+"" ;
            ListenEntity listenEntity1 = new ListenEntity(key,key) ;
            map.put(key,listenEntity1) ;
        }
        igniteCache.putAll(map);


        Set<String> set = new HashSet<>() ;
        set.add("0");
        set.add("1");
        igniteCache.removeAll(set);
        igniteCache.clear("2");
        igniteCache.clear();

        Map<String,ListenEntity> map2 = new HashMap<>() ;
        for (int i = 20; i < 30; i++) {
            String key = i+"" ;
            ListenEntity listenEntity1 = new ListenEntity(key,key) ;
            map2.put(key,listenEntity1) ;
        }
        stmr.addData(map2);
        stmr.flush();

        igniteCache.removeAll();

        igniteCache.put("100",new ListenEntity("100","100"));

        boolean bo = igniteCache.invoke("100", new EntryProcessor<String, ListenEntity, Boolean>() {
            @Override
            public Boolean process(MutableEntry<String, ListenEntity> mutableEntry, Object... objects) throws EntryProcessorException {
                boolean bo = false ;
                try {
                    ListenEntity listenEntity1 = mutableEntry.getValue() ;
                    listenEntity1.setAge("ccc");
                    mutableEntry.setValue(listenEntity1);
                    bo = true ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bo;
            }
        });
        System.out.println("----ep"+bo);
    }

    @After
    public void after(){
        if (ignite!=null){
            IgniteUtil.release(ignite);
        }
        if (igniteCache!=null){
            igniteCache.close();
        }
        if (stmr!=null){
            stmr.close();
        }

    }

    private CacheConfiguration<String,ListenEntity> getCacheConfiguration(){
        CacheConfiguration<String,ListenEntity> cacheConfiguration = new CacheConfiguration<>() ;
        cacheConfiguration.setName(ListenEntity.class.getSimpleName().toUpperCase()) ;
        //备份数量
        cacheConfiguration.setBackups(0) ;
        cacheConfiguration.setSqlSchema("listen") ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setIndexedTypes( String.class ,ListenEntity.class ) ;
        //默认为异步的再平衡
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC) ;
        //一分钟过期
        //cacheConfiguration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        return cacheConfiguration ;
    }
}
