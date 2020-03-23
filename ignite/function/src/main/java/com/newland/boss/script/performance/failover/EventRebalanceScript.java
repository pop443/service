package com.newland.boss.script.performance.failover;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.events.CacheRebalancingEvent;
import org.apache.ignite.events.Event;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.lang.IgnitePredicate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class EventRebalanceScript {

    public void start() {

        Ignite ignite = IgniteUtil.getIgnite() ;
        IgnitePredicate<Event> remoteLsnr = new IgnitePredicate<Event>() {
            @Override
            public boolean apply(Event event) {
                System.out.println("服务端开始过滤");
                return true; // Continue listening.
            }
        };
        Map<String,AtomicLong> map = new HashMap<>() ;
        IgniteBiPredicate<UUID, Event> locLsnr = new IgniteBiPredicate<UUID, Event>() {
            @Override
            public boolean apply(UUID uuid, Event event) {
                if (event instanceof CacheRebalancingEvent){
                    CacheRebalancingEvent cre = (CacheRebalancingEvent)event ;
                    String cacheName = cre.cacheName() ;
                    if (!map.containsKey(cacheName)){
                        map.put(cacheName,new AtomicLong()) ;
                    }
                    int type = cre.type();
                    if (type==EventType.EVT_CACHE_REBALANCE_STARTED){
                        System.out.println("节点："+uuid.toString()+";事件:REBALANCE_STARTED");
                        map.get(cacheName).addAndGet(0-System.currentTimeMillis());
                    }else if (type==EventType.EVT_CACHE_REBALANCE_STOPPED){
                        System.out.println("节点："+uuid.toString()+";事件:REBALANCE_STOPPED");
                        map.get(cacheName).addAndGet(System.currentTimeMillis());
                    }
                    System.out.println("缓存："+cre.cacheName()+";事件"+cre.name()+";分区"+cre.partition());
                }
                return true;
            }
        };
        ignite.events().remoteListen(locLsnr, remoteLsnr,
                EventType.EVT_CACHE_REBALANCE_STARTED, EventType.EVT_CACHE_REBALANCE_STOPPED);

        Timer timer = new Timer("testTimer") ;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("扫描");
                map.forEach((key,value)->{
                    System.out.println(key+"--"+value.get());
                });
            }
        }, 1000,2000);

    }

    public static void main(String[] args) {
        EventRebalanceScript scirpt = new EventRebalanceScript() ;
        scirpt.start();
    }
}
