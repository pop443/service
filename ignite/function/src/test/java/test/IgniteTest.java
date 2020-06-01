package test;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cache.eviction.AbstractEvictionPolicyFactory;
import org.apache.ignite.cache.eviction.EvictionPolicy;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.cache.store.CacheStoreSessionListener;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.internal.IgniteKernal;
import org.apache.ignite.internal.processors.cache.distributed.dht.GridDhtCacheAdapter;

import javax.cache.CacheException;
import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.*;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by Administrator on 2019/12/25.
 */
public class IgniteTest {
    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite();
        try {
            //ignite.cacheNames().forEach(System.out::println);
            IgniteCache igniteCache = ignite.cache("NEARSMALLCUSTOBJ");
            CacheConfiguration configuration = (CacheConfiguration)igniteCache.getConfiguration(CacheConfiguration.class);
            Factory<EvictionPolicy> evictionPolicyFactory = configuration.getNearConfiguration().getNearEvictionPolicyFactory() ;
                if (evictionPolicyFactory instanceof AbstractEvictionPolicyFactory){
                    AbstractEvictionPolicyFactory classFactory = (AbstractEvictionPolicyFactory)evictionPolicyFactory ;

                    System.out.println("--------------"+classFactory.getBatchSize()+"---"+classFactory.getMaxMemorySize()+"--"+classFactory.getMaxSize());
                }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IgniteUtil.release(ignite);
        }
    }
}
