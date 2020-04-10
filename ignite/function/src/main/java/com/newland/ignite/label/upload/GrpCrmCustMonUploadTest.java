package com.newland.ignite.label.upload;

import com.newland.ignite.label.entity.GrpCrmCustMon;
import com.newland.ignite.label.entity.GrpCrmCustMonConfiguration;
import com.newland.ignite.label.prepare.GrpCrmCustMonUpload;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JVM -Xms4g -Xmx4g -Xmn1536m
 */
public class GrpCrmCustMonUploadTest {
    private Ignite ignite ;
    private GrpCrmCustMonConfiguration cfg ;
    private IgniteCache<String,GrpCrmCustMon> igniteCache ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        cfg = new GrpCrmCustMonConfiguration() ;
        igniteCache = cfg.getIgniteCache(ignite) ;
    }
    @Test
    public void upload(){
        GrpCrmCustMonUpload grpCrmCustMonUpload = new GrpCrmCustMonUpload(200000L) ;
        grpCrmCustMonUpload.start(ignite,cfg);

    }

    @After
    public void after(){
        cfg.close();
        IgniteUtil.release(ignite);
    }
}
