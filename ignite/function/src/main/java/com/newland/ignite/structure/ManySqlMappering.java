package com.newland.ignite.structure;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by xz on 2020/7/15.
 */
public class ManySqlMappering {
    private Ignite ignite = null ;
    private IgniteCache igniteCache = null ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("test");
        cacheConfiguration.setIndexedTypes(String.class, PartitionCustObj.class, String.class, ChangeData.class);
        igniteCache = ignite.getOrCreateCache(cacheConfiguration);
    }

    @Test
    public void test(){
        igniteCache.put("2", new PartitionCustObj("2", "2"));
        igniteCache.put("1", new ChangeData("1", 1, 1L, 1D, new Timestamp(System.currentTimeMillis()), new Date(), "2", "2", "2"));
        Object o1 = igniteCache.get("1");
        Object o2 = igniteCache.get("2");
        System.out.println(1);
    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
