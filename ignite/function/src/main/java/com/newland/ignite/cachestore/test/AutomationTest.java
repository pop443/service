package com.newland.ignite.cachestore.test;

import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.AutomationConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by xz on 2020/2/9.
 */
public class AutomationTest {
    private AutomationConfiguration cfg = null ;
    private Ignite ignite = null ;
    private IgniteCache<String,Automation> igniteCache = null ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgniteByXml("node-config-simpleDS.xml") ;
        cfg = new AutomationConfiguration() ;
        igniteCache = cfg.getIgniteCache(ignite) ;
    }
    @Test
    public void destroyCache(){
        ignite.destroyCache(cfg.getCacheName());
    }
    @Test
    public void get(){
        Automation automation = igniteCache.get("1") ;
        System.out.println(automation);
    }
    @Test
    public void put(){
        String key ="1" ;
        Automation automation = new Automation(key,2,"3");
        try {
            igniteCache.put(key,automation);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Automation automation = igniteCache.get("3") ;
        System.out.println(automation);
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
