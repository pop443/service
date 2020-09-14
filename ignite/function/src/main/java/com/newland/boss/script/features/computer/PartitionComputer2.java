package com.newland.boss.script.features.computer;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.ignite.cachestore.adapter.ExpiryCacheStore;
import com.newland.ignite.cachestore.listen.DruidCacheStoreSessionListen;
import com.newland.ignite.label.entity.BossAffinity;
import com.newland.ignite.label.entity.BossAffinityConfiguration;
import com.newland.ignite.label.entity.BossAffinityKey;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.*;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.binary.BinaryObjectBuilder;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.affinity.rendezvous.RendezvousAffinityFunction;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.lang.IgniteClosure;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.LoggerResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2020/8/27.
 */
public class PartitionComputer2 {
    private Ignite ignite ;
    private String name ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        BossAffinityConfiguration cfg = new BossAffinityConfiguration();
        IgniteCache<BossAffinityKey,BossAffinity> igniteCache = ignite.getOrCreateCache(cfg.getCacheConfiguration()) ;
        name = igniteCache.getName() ;
    }

    @Test
    public void broadcastIgniteClosurePut(){
        Collection<ClusterNode> collection = ignite.cluster().forServers().nodes() ;
        ClusterNode[] clusterNodes = collection.toArray(new ClusterNode[collection.size()]) ;
        Map<UUID,Integer> indexMap = new HashMap<>(collection.size()) ;
        int index = 0 ;
        for (ClusterNode clusterNode:collection) {
            indexMap.put((UUID)clusterNode.consistentId(),++index) ;
        }
        for (Map.Entry<UUID,Integer> entry:indexMap.entrySet()) {
            System.out.println(entry.getKey().toString()+"--"+entry.getValue());
        }
        IgniteCompute compute = ignite.compute(ignite.cluster().forNode(clusterNodes[0]));
        Map<String,Object> map = new HashMap<>() ;
        map.put("cachename",name) ;
        map.put("indexMap",indexMap) ;
        IgniteFuture<Collection<List<BinaryObject>>> igniteFuture = compute.broadcastAsync(new CustIgniteClosure(),map) ;
        Collection<List<BinaryObject>> booleans = igniteFuture.get(5, TimeUnit.SECONDS) ;
        for (List<BinaryObject> list:booleans) {
            for (BinaryObject binaryObject:list) {
                BossAffinity bossAffinity = binaryObject.deserialize() ;
                System.out.println(bossAffinity);
            }
        }
    }


    @After
    public void after(){
        IgniteUtil.release(ignite);
    }

    class CustIgniteClosure implements IgniteClosure<Map<String,Object>, List<BinaryObject>>{
        @IgniteInstanceResource
        private Ignite ignite;

        @LoggerResource
        private IgniteLogger log;

        @Override
        public List<BinaryObject> apply(Map<String, Object> map) {
            return deal(map,ignite,log);
        }
    }
    public static List<BinaryObject> deal(Map<String,Object> map, Ignite ignite, IgniteLogger log) {
        try {

            Map<UUID,Integer> indexMap = (Map<UUID,Integer>)map.get("indexMap") ;
            UUID uuid = (UUID)ignite.cluster().localNode().consistentId() ;
            Integer index = indexMap.get(uuid) ;
            log.info("\r\n--"+index+"\r\n");
            List<BinaryObject> list = new ArrayList<>() ;
            for (int i = 0; i < 2; i++) {
                IgniteBinary igniteBinary = ignite.binary() ;
                BinaryObjectBuilder builder1 = igniteBinary.builder("com.newland.ignite.label.entity.BossAffinity") ;
                builder1.setField("user_id1",1L) ;
                List<BinaryObject> list2 = new ArrayList<>() ;
                for (int j = 0; j < 2; j++) {
                    BinaryObjectBuilder builder2 = igniteBinary.builder("com.newland.ignite.label.entity.BossIndex") ;
                    builder2.setField("joint1","1") ;
                    list2.add(builder2.build()) ;
                }
                builder1.setField("list2",list2) ;
                list.add(builder1.build()) ;
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("---------Exception:"+e.getMessage());
        }


        return null;
    }
}
