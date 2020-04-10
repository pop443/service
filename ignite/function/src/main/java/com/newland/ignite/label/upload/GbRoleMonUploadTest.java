package com.newland.ignite.label.upload;

import com.newland.ignite.label.entity.GbRoleMon;
import com.newland.ignite.label.entity.GbRoleMonConfiguration;
import com.newland.ignite.label.prepare.GbRoleMonUpload;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.lang.IgniteFuture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * JVM -Xms4g -Xmx4g -Xmn1536m
 */
public class GbRoleMonUploadTest {
    private Ignite ignite ;
    private GbRoleMonConfiguration cfg ;
    private IgniteCache<String,GbRoleMon> igniteCache ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        cfg = new GbRoleMonConfiguration() ;
        igniteCache = cfg.getIgniteCache(ignite) ;
    }
    @Test
    public void destroyCache(){
        long l1 = System.currentTimeMillis() ;
        ignite.destroyCache("GBROLEMON");
        long l2 = System.currentTimeMillis() ;
        System.out.println("--------------------------"+(l2-l1));
    }
    @Test
    public void upload(){
        GbRoleMonUpload gbRoleMonUpload = new GbRoleMonUpload(4000000L) ;
        gbRoleMonUpload.start(ignite,cfg);

    }
    @Test
    public void put(){
        Map<String,GbRoleMon> map = new HashMap<>() ;
        for (int i = 0; i < 10; i++) {
            String key = i+"" ;
            GbRoleMon gbRoleMon = new GbRoleMon() ;
            gbRoleMon.setApp_cnt(i);
            map.put(key,gbRoleMon) ;
        }
        igniteCache.putAll(map);
    }

    /**
     * 100000 59.556 destroyCache 3.9
     */
    @Test
    public void clear(){
        long l1 = System.currentTimeMillis() ;
        igniteCache.clear();
        long l2 = System.currentTimeMillis() ;
        System.out.println("--------------------------"+(l2-l1));
    }

    /**
     * 100000 77 destroyCache 597
     */
    @Test
    public void removeAll(){
        long l1 = System.currentTimeMillis() ;
        igniteCache.removeAll();
        long l2 = System.currentTimeMillis() ;
        System.out.println("--------------------------"+(l2-l1));
    }
    @Test
    public void remove(){
        igniteCache.remove("1");
    }



    @Test
    public void rebalance(){
        IgniteFuture<Boolean> igniteFuture = igniteCache.rebalance() ;
        System.out.println(igniteFuture.get());
    }

    @After
    public void after(){
        cfg.close();
        IgniteUtil.release(ignite);
    }
}
