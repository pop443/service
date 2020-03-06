package ignite.entity.upload;

import com.xz.ignite.basefunction.entity.UserPackageDayTemp;
import com.xz.ignite.basefunction.entity.upload.UserPackageDayTempUpload;
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
public class UserPackageDayTempUploadTest {
    private Ignite ignite ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void upload(){
        CacheConfiguration<String,UserPackageDayTemp> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, UserPackageDayTemp.class) ;
        UserPackageDayTempUpload userPackageDayTempUpload = new UserPackageDayTempUpload(1000000L) ;
        userPackageDayTempUpload.start(ignite,cacheConfiguration);

    }

    @Test
    public void put(){
        IgniteCache<String,UserPackageDayTemp> igniteCache = ignite.cache("USERPACKAGEDAYTEMP") ;
        Map<String,UserPackageDayTemp> map = new HashMap<>() ;
        for (int i = 3000000; i < 3010000; i++) {
            String key = i+"" ;
            System.out.println(key);
            UserPackageDayTemp userPackageDayTemp = new UserPackageDayTemp() ;
            userPackageDayTemp.setApply_date(i);
            map.put(key,userPackageDayTemp) ;
        }
        IgniteFuture<Void> igniteFuture = igniteCache.putAllAsync(map);
        System.out.println(igniteFuture.get());

    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
