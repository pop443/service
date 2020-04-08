package com.newland.ignite.cachestore.test;

import com.newland.ignite.cachestore.entity.Course;
import com.newland.ignite.cachestore.entity.CourseConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.util.concurrent.locks.Lock;

/**
 * Created by xz on 2020/2/9.
 */
public class CourseTest {
    private Ignite ignite = null ;
    private CourseConfiguration cfg = null ;
    private IgniteCache<String,Course> igniteCache = null ;

    @Before
    public void before(){
        cfg = new CourseConfiguration() ;
        ignite = IgniteUtil.getIgniteByXml("node-config-manyDS.xml") ;
        igniteCache = cfg.getIgniteCache(ignite) ;
    }
    @Test
    public void ep(){
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < 1; i++) {
            igniteCache.invoke("1", new CacheEntryProcessor<String, Course, Object>() {
                @Override
                public Object process(MutableEntry<String, Course> mutableEntry, Object... objects) throws EntryProcessorException {
                    System.out.println(mutableEntry.getValue());
                    return null;
                }
            });
        }
        long l2 = System.currentTimeMillis() ;
        System.out.println(l2-l1);
    }
    @Test
    public void lock(){
        long l1 = System.currentTimeMillis() ;
        Lock lock = igniteCache.lock("1");
        for (int i = 0; i < 1; i++) {
            lock.lock();
            System.out.println(igniteCache.get("1"));
            lock.unlock();
        }
        long l2 = System.currentTimeMillis() ;
        System.out.println(l2-l1);
    }
    @Test
    public void destroyCache(){
        ignite.destroyCache(cfg.getCacheName());
    }
    @Test
    public void get(){
        Course course = igniteCache.get("30") ;
        System.out.println(course);
    }
    @Test
    public void put(){
        String key ="30" ;
        Course course = new Course(key,key,key);
        try {
            igniteCache.put(key,course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----put-----");
        //Course result = igniteCache.get(key) ;
        //System.out.println(result);
    }

    /**
     * 删除ignite缓存
     * 调用cachestore delete 删除第三方持久化
     */
    @Test
    public void remove(){
        String key = "6" ;
        igniteCache.remove(key);
    }
    /**
     * 删除ignite缓存
     */
    @Test
    public void clear(){
        igniteCache.clear("3");
        Course course = igniteCache.get("3") ;
        System.out.println(course);
    }

    @Test
    public void clearAll(){
        igniteCache.clear();
    }


    @After
    public void after(){
        cfg.close();
        if (ignite!=null){
            IgniteUtil.release(ignite);
        }
    }

}
