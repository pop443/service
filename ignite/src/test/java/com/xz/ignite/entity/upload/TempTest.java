package com.xz.ignite.entity.upload;

import com.xz.ignite.basefunction.entity.Temp;
import com.xz.ignite.utils.CacheConfigurationUtil;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 INSERT INTO TEMP (_key,COL1,COL2,COL3,COL4,COL5)
 SELECT uuid(),'1','1','1','1','1' UNION ALL
 SELECT uuid(),'2','2','2','2','2'
 */
public class TempTest {
    private Ignite ignite ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void upload(){
        CacheConfiguration<UUID,Temp> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(UUID.class, Temp.class) ;
        ignite.destroyCache(cacheConfiguration.getName());
        IgniteCache<UUID,Temp> igniteCache = ignite.getOrCreateCache(cacheConfiguration) ;
        igniteCache.putIfAbsent(new UUID(1,1),new Temp("1","1","1","1","1")) ;
        igniteCache.putIfAbsent(new UUID(2,2),new Temp("2","2","2","2","2")) ;

    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
