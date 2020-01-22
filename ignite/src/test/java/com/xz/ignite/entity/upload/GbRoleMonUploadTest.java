package com.xz.ignite.entity.upload;

import com.xz.ignite.basefunction.entity.GbRoleMon;
import com.xz.ignite.basefunction.entity.upload.GbRoleMonUpload;
import com.xz.ignite.utils.CacheConfigurationUtil;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteFuture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JVM -Xms4g -Xmx4g -Xmn1536m
 */
public class GbRoleMonUploadTest {
    private Ignite ignite ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void upload(){
        CacheConfiguration<String,GbRoleMon> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, GbRoleMon.class) ;
        GbRoleMonUpload gbRoleMonUpload = new GbRoleMonUpload(2000000L) ;
        gbRoleMonUpload.start(ignite,cacheConfiguration);

    }

    @Test
    public void rebalance(){
        CacheConfiguration<String,GbRoleMon> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, GbRoleMon.class) ;
        IgniteCache<String,GbRoleMon> igniteCache = ignite.cache(cacheConfiguration.getName()) ;
        igniteCache.enableStatistics(true);
        IgniteFuture<Boolean> igniteFuture = igniteCache.rebalance() ;
        System.out.println(igniteFuture.get());
    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
