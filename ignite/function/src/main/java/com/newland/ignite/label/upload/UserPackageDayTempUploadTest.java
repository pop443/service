package com.newland.ignite.label.upload;

import com.newland.ignite.label.entity.UserAttrDayTemp;
import com.newland.ignite.label.entity.UserAttrDayTempConfiguration;
import com.newland.ignite.label.entity.UserPackageDayTempConfiguration;
import com.newland.ignite.label.prepare.UserPackageDayTempUpload;
import com.newland.ignite.label.entity.UserPackageDayTemp;
import com.newland.ignite.utils.CustCacheConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteFuture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * JVM -Xms4g -Xmx4g -Xmn1536m
 */
public class UserPackageDayTempUploadTest {
    private Ignite ignite ;
    private UserPackageDayTempConfiguration cfg ;
    private IgniteCache<String,UserPackageDayTemp> igniteCache ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        cfg = new UserPackageDayTempConfiguration() ;
        igniteCache = cfg.getIgniteCache(ignite) ;
    }
    @Test
    public void upload(){
        UserPackageDayTempUpload userPackageDayTempUpload = new UserPackageDayTempUpload(2000000L) ;
        userPackageDayTempUpload.start(ignite,cfg);
    }

    @Test
    public void drop(){
        ignite.destroyCache(cfg.getCacheName());

    }

    @Test
    public void put(){
        Map<String,UserPackageDayTemp> map = new HashMap<>() ;
        for (int i = 3000000; i < 3010000; i++) {
            String key = i+"" ;
            System.out.println(key);
            UserPackageDayTemp userPackageDayTemp = new UserPackageDayTemp() ;
            userPackageDayTemp.setApply_date(i);
            map.put(key,userPackageDayTemp) ;
        }
        IgniteFuture<Void> igniteFuture = igniteCache.putAllAsync(map);
        System.out.println(igniteFuture.get());

    }

    @After
    public void after(){
        cfg.close();
        IgniteUtil.release(ignite);
    }
}
