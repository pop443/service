package com.newland.ignite.structure;

import com.newland.ignite.cachestore.entity.Course;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by xz on 2020/3/30.
 */
public class ChangeDataTest {
    private Ignite ignite = null ;
    private ChangeDataConfiguration cfg = null ;
    private IgniteCache<String,ChangeData> igniteCache = null ;

    @Before
    public void before(){
        cfg = new ChangeDataConfiguration() ;
        ignite = IgniteUtil.getIgnite() ;
        igniteCache = cfg.getIgniteCache(ignite) ;
    }

    @Test
    public void destroyCache(){
        ignite.destroyCache(cfg.getCacheName());
    }

    @Test
    public void get(){
        ChangeData changeData = igniteCache.get("30");
        System.out.println(changeData.getRemark4());
    }

    @Test
    public void put(){
        String key ="30" ;
        ChangeData changeData = new ChangeData(key,1L,1D,new Date(),key,key,key);
        try {
            igniteCache.put(key,changeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----put-----");
    }

    @Test
    public void remove(){
        String key = "6" ;
        igniteCache.remove(key);
    }

    @After
    public void after(){
        cfg.close();
        IgniteUtil.release(ignite);
    }
}
