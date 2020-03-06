package ignite.entity.upload;

import com.xz.ignite.basefunction.entity.GbRoleMon;
import com.xz.ignite.basefunction.entity.upload.GbRoleMonUpload;
import com.xz.ignite.utils.CacheConfigurationUtil;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteFuture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * JVM -Xms4g -Xmx4g -Xmn1536m
 */
public class GbRoleMonUploadTest {
    private Ignite ignite ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }
    @Test
    public void create(){
        CacheConfiguration<String,GbRoleMon> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, GbRoleMon.class) ;
        IgniteCache<String,GbRoleMon> igniteCache = ignite.getOrCreateCache(cacheConfiguration.getName()) ;
    }
    @Test
    public void upload(){
        CacheConfiguration<String,GbRoleMon> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, GbRoleMon.class) ;
        GbRoleMonUpload gbRoleMonUpload = new GbRoleMonUpload(20L) ;
        gbRoleMonUpload.start(ignite,cacheConfiguration);

    }

    @Test
    public void put(){
        IgniteCache<String,GbRoleMon> igniteCache = ignite.cache("GBROLEMON") ;
        Map<String,GbRoleMon> map = new HashMap<>() ;
        for (int i = 0; i < 10; i++) {
            String key = i+"" ;
            GbRoleMon gbRoleMon = new GbRoleMon() ;
            gbRoleMon.setApp_cnt(i);
            map.put(key,gbRoleMon) ;
        }
        igniteCache.putAll(map);
    }




    @Test
    public void destroyCache(){
        long l1 = System.currentTimeMillis() ;
        ignite.destroyCache("GBROLEMON");
        long l2 = System.currentTimeMillis() ;
        System.out.println("--------------------------"+(l2-l1));
    }

    /**
     * 100000 59.556 destroyCache 3.9
     */
    @Test
    public void clear(){
        IgniteCache<String,GbRoleMon> igniteCache = ignite.cache("GBROLEMON") ;
        igniteCache.enableStatistics(true);
        long l1 = System.currentTimeMillis() ;
        igniteCache.clear();
        long l2 = System.currentTimeMillis() ;
        System.out.println("--------------------------"+(l2-l1));
    }

    /**
     * 100000 77 destroyCache 597
     */
    @Test
    public void removeAll(){
        IgniteCache<String,GbRoleMon> igniteCache = ignite.cache("GBROLEMON") ;
        igniteCache.enableStatistics(true);
        long l1 = System.currentTimeMillis() ;
        igniteCache.removeAll();
        long l2 = System.currentTimeMillis() ;
        System.out.println("--------------------------"+(l2-l1));
    }
    @Test
    public void remove(){
        IgniteCache<String,GbRoleMon> igniteCache = ignite.cache("GBROLEMON") ;
        igniteCache.enableStatistics(true);
        igniteCache.remove("1");
    }



    @Test
    public void rebalance(){
        CacheConfiguration<String,GbRoleMon> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, GbRoleMon.class) ;
        IgniteCache<String,GbRoleMon> igniteCache = ignite.cache(cacheConfiguration.getName()) ;
        igniteCache.enableStatistics(true);
        IgniteFuture<Boolean> igniteFuture = igniteCache.rebalance() ;
        System.out.println(igniteFuture.get());
    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
