package com.newland.ignite.continuousquery;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.events.EventAdapter;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgnitePredicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by xz on 2020/3/6.
 */
public class SysListener {
    private Ignite ignite = null ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void listen(){
        ignite.events().localListen(new IgnitePredicate<EventAdapter>() {
            @Override
            public boolean apply(EventAdapter eventAdapter) {
                System.out.println(eventAdapter.getClass());
                System.out.println("Received event [evt=" + eventAdapter.name() + ";node="+eventAdapter.node().id().toString()+"]");
                return true; // Continue listening.
            }
        }, EventType.EVT_CACHE_REBALANCE_PART_DATA_LOST);
        int i = 0 ;
        while (i==0){
            try {
                Thread.sleep(20000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @After
    public void after(){
        if (ignite!=null){
            IgniteUtil.release(ignite);
        }
    }
}
