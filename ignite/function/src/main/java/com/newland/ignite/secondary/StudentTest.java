package com.newland.ignite.secondary;

import com.newland.ignite.secondary.entity.Student;
import com.newland.ignite.secondary.entity.StudentConfiguration;
import com.newland.ignite.secondary.entity.Wear;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/5.
 */
public class StudentTest {
    private Ignite ignite ;
    private StudentConfiguration cfg ;
    private IgniteDataStreamer<String,Student> igniteDataStreamer ;
    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite();
        cfg = new StudentConfiguration() ;
        igniteDataStreamer = cfg.getDataStreamer(ignite) ;
    }
    @Test
    public void destroyCache(){
        ignite.destroyCache(cfg.getCacheName());
    }

    @Test
    public void input(){
        Map<String,Student> map = new HashMap<>() ;
        for (int i = 0; i < 100; i++) {
            String key = i+"" ;
            Wear wear = new Wear("coat"+key,"pants"+key,"shoe"+key) ;
            Student student = new Student(key,key,i,wear) ;
            map.put(key,student) ;
        }
        igniteDataStreamer.keepBinary(true) ;
        igniteDataStreamer.addData(map) ;
        igniteDataStreamer.flush();

    }

    @After
    public void after(){
        cfg.close();
        IgniteUtil.release(ignite);
    }

}
