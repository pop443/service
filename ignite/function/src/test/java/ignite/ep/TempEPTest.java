package ignite.ep;

import com.xz.ignite.basefunction.entity.Temp;
import com.xz.ignite.basefunction.entryprocessor.TempEP;
import com.xz.ignite.basefunction.entryprocessor.TempEPBinary;
import com.xz.ignite.utils.CacheConfigurationUtil;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Administrator on 2020/1/3.
 */
public class TempEPTest {

    private Ignite ignite ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void TempObject(){
        CacheConfiguration<UUID,Temp> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(UUID.class, Temp.class) ;
        IgniteCache<UUID,Temp> igniteCache = ignite.getOrCreateCache(cacheConfiguration) ;
        UUID uuid = new UUID(1L,1L) ;
        Temp temp = new Temp("1","1","1","1","1") ;
        igniteCache.putIfAbsent(uuid,temp) ;
        boolean bo = igniteCache.invoke(uuid,new TempEP(),"a");
        System.out.println(bo);
    }

    @Test
    public void BinaryObject(){
        CacheConfiguration<UUID,Temp> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(UUID.class, Temp.class) ;
        IgniteCache<UUID,BinaryObject> igniteCache = ignite.getOrCreateCache(cacheConfiguration).withKeepBinary() ;
        UUID uuid = new UUID(1L,1L) ;
        Temp temp = new Temp("1","1","1","1","1") ;
        igniteCache.putIfAbsent(uuid,IgniteUtil.toBinary(temp)) ;
        boolean bo = igniteCache.invoke(uuid,new TempEPBinary(),"a") ;
        System.out.println(bo);
    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
