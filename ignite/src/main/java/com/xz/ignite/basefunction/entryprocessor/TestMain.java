package com.xz.ignite.basefunction.entryprocessor;

import com.xz.ignite.utils.CacheConfigurationUtil;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2020/1/7.
 */
public class TestMain {
    private static final Logger LOG = LoggerFactory.getLogger(TestMain.class);
    public static void main(String[] args) {
        LOG.debug("start");
        boolean bo = false ;
        String arg = "aaaa" ;
        try {
            bo = args.length>0 ;
            arg = args[0] ;
        } catch (Exception e) {
            e.printStackTrace();
        }


        Ignite ignite = IgniteUtil.getIgnite() ;
        try {
            CacheConfiguration<String,String> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, String.class) ;
            cacheConfiguration.setName("XZ") ;
            IgniteCache<String,String> igniteCache = ignite.cache(cacheConfiguration.getName()) ;
            if (igniteCache==null){
                igniteCache = ignite.createCache(cacheConfiguration);
                igniteCache.putIfAbsent("1","1") ;
            }
            do {
                boolean bo1 = igniteCache.invoke("1", new TestEP(), arg);
                System.out.println(bo1);
                Thread.sleep(5000);
            }while (bo) ;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IgniteUtil.release(ignite);
        }
    }
}
