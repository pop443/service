package com.newland.ignite.template;

import com.newland.ignite.structure.ChangeData;
import com.newland.ignite.structure.ChangeDataConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by xz on 2020/7/9.
 */
public class CacheTemplate {
    private Ignite ignite = null ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void addTemplate(){
        CacheConfiguration cfg = new CacheConfiguration() ;
        cfg.setName("myCacheTemplate") ;
        cfg.setBackups(2);
        cfg.setCacheMode(CacheMode.PARTITIONED);
        ignite.addCacheConfiguration(cfg);
    }

    @Test
    public void get(){
    }

}
