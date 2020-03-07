package com.newland.ignite.label.upload;

import com.newland.ignite.label.entity.GrpMemberMon;
import com.newland.ignite.label.entity.GrpMemberMonConfiguration;
import com.newland.ignite.label.entity.UserAttrDayTempConfiguration;
import com.newland.ignite.label.prepare.UserAttrDayTempUpload;
import com.newland.ignite.label.entity.UserAttrDayTemp;
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
public class UserAttrDayTempUploadTest {
    private Ignite ignite ;
    private UserAttrDayTempConfiguration cfg ;
    private IgniteCache<String,UserAttrDayTemp> igniteCache ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        cfg = new UserAttrDayTempConfiguration() ;
        igniteCache = cfg.getIgniteCache(ignite) ;
    }
    @Test
    public void upload(){
        UserAttrDayTempUpload userAttrDayTempUpload = new UserAttrDayTempUpload(200L) ;
        userAttrDayTempUpload.start(ignite,cfg);
    }

    @After
    public void after(){
        cfg.close();
        IgniteUtil.release(ignite);
    }
}
