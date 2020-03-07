package com.newland.ignite.label.upload;

import com.newland.ignite.label.entity.GrpCrmCustMon;
import com.newland.ignite.label.entity.GrpCrmCustMonConfiguration;
import com.newland.ignite.label.entity.GrpMemberMonConfiguration;
import com.newland.ignite.label.prepare.GrpMemberMonUpload;
import com.newland.ignite.label.entity.GrpMemberMon;
import com.newland.ignite.utils.CustCacheConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JVM -Xms4g -Xmx4g -Xmn1536m
 */
public class GrpMemberMonUploadTest {
    private Ignite ignite ;
    private GrpMemberMonConfiguration cfg ;
    private IgniteCache<String,GrpMemberMon> igniteCache ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        cfg = new GrpMemberMonConfiguration() ;
        igniteCache = cfg.getIgniteCache(ignite) ;
    }

    @Test
    public void upload(){
        GrpMemberMonUpload grpMemberMonUpload = new GrpMemberMonUpload(50000L) ;
        grpMemberMonUpload.start(ignite,cfg);

    }

    @After
    public void after(){
        cfg.close();
        IgniteUtil.release(ignite);
    }
}
