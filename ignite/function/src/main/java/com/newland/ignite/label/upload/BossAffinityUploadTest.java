package com.newland.ignite.label.upload;

import com.newland.ignite.label.entity.*;
import com.newland.ignite.label.prepare.BossAffinityUpload;
import com.newland.ignite.label.prepare.BossIndexUpload;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JVM -Xms4g -Xmx4g -Xmn1536m
 */
public class BossAffinityUploadTest {
    private Ignite ignite ;
    private BossAffinityConfiguration cfg ;
    private IgniteCache<BossAffinityKey,BossAffinity> igniteCache ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        cfg = new BossAffinityConfiguration() ;
        igniteCache = ignite.getOrCreateCache(cfg.getCacheConfiguration());
    }
    @Test
    public void drop(){
        ignite.destroyCache(cfg.getCacheName());

    }
    @Test
    public void upload(){
        BossAffinityUpload bossAffinityUpload = new BossAffinityUpload(1000000L) ;
        bossAffinityUpload.start(ignite,cfg);
    }

    @Test
    public void get(){

        BossAffinityKey bossAffinityKey = new BossAffinityKey() ;
        bossAffinityKey.setId("2000000000234058");
        bossAffinityKey.setName("13675110436");
        BossAffinity bossAffinity = igniteCache.get(bossAffinityKey) ;
        System.out.println(bossAffinity);
    }

    @After
    public void after(){
        cfg.close();
        IgniteUtil.release(ignite);
    }
}
