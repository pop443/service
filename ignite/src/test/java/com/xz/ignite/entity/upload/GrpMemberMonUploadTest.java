package com.xz.ignite.entity.upload;

import com.xz.ignite.basefunction.entity.GrpMemberMon;
import com.xz.ignite.basefunction.entity.upload.GrpMemberMonUpload;
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
public class GrpMemberMonUploadTest {
    private Ignite ignite ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void upload(){
        CacheConfiguration<String,GrpMemberMon> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, GrpMemberMon.class) ;
        GrpMemberMonUpload grpMemberMonUpload = new GrpMemberMonUpload(50000L) ;
        grpMemberMonUpload.start(ignite,cacheConfiguration);

    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
