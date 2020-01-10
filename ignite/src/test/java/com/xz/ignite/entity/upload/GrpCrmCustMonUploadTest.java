package com.xz.ignite.entity.upload;

import com.xz.ignite.basefunction.entity.GrpCrmCustMon;
import com.xz.ignite.basefunction.entity.upload.GrpCrmCustMonUpload;
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
public class GrpCrmCustMonUploadTest {
    private Ignite ignite ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void upload(){
        CacheConfiguration<String,GrpCrmCustMon> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, GrpCrmCustMon.class) ;
        GrpCrmCustMonUpload grpCrmCustMonUpload = new GrpCrmCustMonUpload() ;
        grpCrmCustMonUpload.start(ignite,cacheConfiguration);

    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
