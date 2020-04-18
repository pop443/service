package com.newland.ignite.label.upload;

import com.newland.ignite.label.entity.BossIndex;
import com.newland.ignite.label.entity.BossIndexConfiguration;
import com.newland.ignite.label.entity.UserPackageDayTemp;
import com.newland.ignite.label.entity.UserPackageDayTempConfiguration;
import com.newland.ignite.label.prepare.BossIndexUpload;
import com.newland.ignite.label.prepare.UserPackageDayTempUpload;
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
public class BossIndexUploadTest {
    private Ignite ignite ;
    private BossIndexConfiguration cfg ;
    private IgniteCache<String,BossIndex> igniteCache ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        cfg = new BossIndexConfiguration() ;
        igniteCache = ignite.getOrCreateCache(cfg.getCacheConfiguration());
    }
    @Test
    public void drop(){
        ignite.destroyCache(cfg.getCacheName());

    }
    @Test
    public void upload(){
        BossIndexUpload bossIndexUpload = new BossIndexUpload(100000L) ;
        bossIndexUpload.start(ignite,cfg);
    }

    @Test
    public void put(){

    }

    @After
    public void after(){
        cfg.close();
        IgniteUtil.release(ignite);
    }
}
