package com.newland.boss.script.features.event;

import com.newland.boss.entity.event.Event;
import com.newland.boss.entity.event.EventConfiguration;
import com.newland.boss.script.features.BaseScript;
import com.newland.ignite.continusquery.CustCacheEntryEventFilter;
import org.apache.ignite.cache.query.ContinuousQuery;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.events.CacheRebalancingEvent;
import org.apache.ignite.events.EventAdapter;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgnitePredicate;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryUpdatedListener;

/**
 * 3.8.1 3.8.2 监听系统事件和缓存事件
 */
public class TestEventListener extends BaseScript<String,Event> {
    public TestEventListener() {
        super(new EventConfiguration());
    }

    @Override
    public void work() {
        //系统事件 数据丢失事件
        IgnitePredicate<EventAdapter> locLsnr = new IgnitePredicate<EventAdapter>() {
            @Override
            public boolean apply(EventAdapter eventAdapter) {
                System.out.println("----------------------");
                if (eventAdapter instanceof CacheRebalancingEvent){
                    CacheRebalancingEvent event = (CacheRebalancingEvent)eventAdapter ;
                    System.out.println("CacheRebalancingEvent event [evt=" + eventAdapter.name() +";partition="+event.partition()+";node="+eventAdapter.node().id().toString()+"]");
                }
                return true; // Continue listening.
            }
        };
        ignite.events().localListen(locLsnr,
                EventType.EVT_CACHE_REBALANCE_PART_DATA_LOST);


        ContinuousQuery<Object, Object> qry = new ContinuousQuery<>();
        //初始化查询
        qry.setInitialQuery(new ScanQuery<>());
        //本地监听
        qry.setLocalListener(new CacheEntryUpdatedListener<Object, Object>() {
            @Override
            public void onUpdated(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> iterable) throws CacheEntryListenerException {
                for (CacheEntryEvent<? extends Object, ? extends Object> e: iterable){

                    javax.cache.event.EventType eventType = e.getEventType();
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
                    Object oldValue = null ;
                    if (e.isOldValueAvailable()){
                        oldValue = e.getOldValue();
                    }
                    System.out.println("key=" + e.getKey() + ", newVal=" + e.getValue() + ", oldVal=" + oldValue);
                }
            }
        });
        //远程节点过滤
        qry.setRemoteFilterFactory(FactoryBuilder.factoryOf(CustCacheEntryEventFilter.class));
        qry.setIncludeExpired(true);
        igniteCache.query(qry) ;


    }

    @Override
    protected void destory() {
        //super.destory();
    }

    public static void main(String[] args) {
        TestEventListener scirpt = new TestEventListener() ;
        scirpt.start();
    }
}
