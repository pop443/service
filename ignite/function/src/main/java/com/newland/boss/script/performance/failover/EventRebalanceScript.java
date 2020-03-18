package com.newland.boss.script.performance.failover;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.features.BaseScript;
import org.apache.ignite.events.CacheRebalancingEvent;
import org.apache.ignite.events.EventAdapter;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgnitePredicate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class EventRebalanceScript extends BaseScript<String,PartitionCustObj> {
    public EventRebalanceScript() {
        super(new PartitionCustObjConfiguration());
    }

    @Override
    public void work() {
        //系统事件 数据丢失事件
        IgnitePredicate<EventAdapter> locLsnr = new IgnitePredicate<EventAdapter>() {
            private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
            @Override
            public boolean apply(EventAdapter eventAdapter) {
                System.out.println("再平衡开始和结束时间");
                if (eventAdapter instanceof CacheRebalancingEvent){
                    CacheRebalancingEvent event = (CacheRebalancingEvent)eventAdapter ;
                    System.out.println("time:"+simpleDateFormat.format(new Date())+";event [evt=" + eventAdapter.name() +";partition="+event.partition()+";node="+eventAdapter.node().id().toString()+"]");
                }
                return true; // Continue listening.
            }
        };
        ignite.events().localListen(locLsnr,
                EventType.EVT_CACHE_REBALANCE_STARTED,EventType.EVT_CACHE_REBALANCE_STOPPED);
    }

    @Override
    protected void destory() {
        //super.destory();
    }

    public static void main(String[] args) {
        EventRebalanceScript scirpt = new EventRebalanceScript() ;
        scirpt.start();
    }
}
