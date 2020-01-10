package com.xz.ignite.entity.upload;

import com.xz.ignite.basefunction.entity.UserPackageDayTemp;
import com.xz.ignite.basefunction.entity.upload.UserPackageDayTempUpload;
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
public class UserPackageDayTempUploadTest {
    private Ignite ignite ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void upload(){
        CacheConfiguration<String,UserPackageDayTemp> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, UserPackageDayTemp.class) ;
        UserPackageDayTempUpload userPackageDayTempUpload = new UserPackageDayTempUpload() ;
        userPackageDayTempUpload.start(ignite,cacheConfiguration);

    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
