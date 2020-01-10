package com.xz.ignite.entity.upload;

import com.xz.ignite.basefunction.entity.GbRoleMon;
import com.xz.ignite.basefunction.entity.upload.GbRoleMonUpload;
import com.xz.ignite.utils.CacheConfigurationUtil;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;
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
        GbRoleMonUpload gbRoleMonUpload = new GbRoleMonUpload() ;
        gbRoleMonUpload.start(ignite,cacheConfiguration);

    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
