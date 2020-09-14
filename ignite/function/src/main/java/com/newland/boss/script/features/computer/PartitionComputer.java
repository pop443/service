package com.newland.boss.script.features.computer;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.ignite.computer.ApplyDemo2;
import com.newland.ignite.computer.CallableDemo1;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.*;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.binary.BinaryObjectBuilder;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeJobResultPolicy;
import org.apache.ignite.compute.ComputeTask;
import org.apache.ignite.internal.binary.builder.BinaryObjectBuilderImpl;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.lang.IgniteClosure;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.LoggerResource;
import org.apache.ignite.transactions.TransactionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.CacheException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2020/8/27.
 */
public class PartitionComputer {
    private Ignite ignite ;
    private String name ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        PartitionCustObjConfiguration bigcfg = new PartitionCustObjConfiguration();
        IgniteCache<String,PartitionCustObj> igniteCache = ignite.getOrCreateCache(bigcfg.getCacheConfiguration()) ;
        name = igniteCache.getName() ;
    }


    @Test
    public void broadcastIgniteClosurePut(){
        Collection<ClusterNode> collection = ignite.cluster().forServers().nodes() ;
        Map<UUID,Integer> indexMap = new HashMap<>(collection.size()) ;
        int index = 0 ;
        for (ClusterNode clusterNode:collection) {
            indexMap.put((UUID)clusterNode.consistentId(),++index) ;
        }
        for (Map.Entry<UUID,Integer> entry:indexMap.entrySet()) {
            System.out.println(entry.getKey().toString()+"--"+entry.getValue());
        }
        IgniteCompute compute = ignite.compute();
        Map<String,Object> map = new HashMap<>() ;
        map.put("cachename",name) ;
        map.put("sql","sql") ;
        map.put("indexMap",indexMap) ;
        IgniteFuture<Collection<Boolean>> igniteFuture = compute.broadcastAsync(new CustIgniteClosure(),map) ;
        Collection<Boolean> booleans = igniteFuture.get(5, TimeUnit.SECONDS) ;
        System.out.println(booleans.size());

    }

    @Test
    public void broadcastIgniteCalledPut(){
        Collection<ClusterNode> collection = ignite.cluster().forServers().nodes() ;
        Map<UUID,Integer> indexMap = new HashMap<>(collection.size()) ;
        int index = 0 ;
        for (ClusterNode clusterNode:collection) {
            indexMap.put((UUID)clusterNode.consistentId(),++index) ;
        }
        for (Map.Entry<UUID,Integer> entry:indexMap.entrySet()) {
            System.out.println(entry.getKey().toString()+"--"+entry.getValue());
        }
        IgniteCompute compute = ignite.compute();
        Map<String,Object> map = new HashMap<>() ;
        map.put("cachename",name) ;
        map.put("sql","sql") ;
        map.put("indexMap",indexMap) ;
        IgniteFuture<Collection<Boolean>> igniteFuture = compute.broadcastAsync(new CustIgniteCallable(map)) ;
        Collection<Boolean> booleans = igniteFuture.get(5, TimeUnit.SECONDS) ;
        System.out.println(booleans.size());
    }

    @Test
    public void broadcastIgniteRunnable(){
        Collection<ClusterNode> collection = ignite.cluster().forServers().nodes() ;
        Map<UUID,Integer> indexMap = new HashMap<>(collection.size()) ;
        int index = 0 ;
        for (ClusterNode clusterNode:collection) {
            indexMap.put((UUID)clusterNode.consistentId(),++index) ;
        }
        for (Map.Entry<UUID,Integer> entry:indexMap.entrySet()) {
            System.out.println(entry.getKey().toString()+"--"+entry.getValue());
        }
        IgniteCompute compute = ignite.compute();
        Map<String,Object> map = new HashMap<>() ;
        map.put("cachename",name) ;
        map.put("sql","sql") ;
        map.put("indexMap",indexMap) ;
        IgniteFuture<Void> igniteFuture = compute.broadcastAsync(new CustIgniteRunnable(map)) ;
        Void aVoid = igniteFuture.get(5, TimeUnit.SECONDS) ;
        System.out.println(aVoid);
    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }

    class CustIgniteClosure implements IgniteClosure<Map<String,Object>, Boolean>{
        @IgniteInstanceResource
        private Ignite ignite;

        @LoggerResource
        private IgniteLogger log;

        @Override
        public Boolean apply(Map<String, Object> map) {
            return deal(map,ignite,log);
        }
    }
    class CustIgniteCallable implements IgniteCallable<Boolean>{
        private Map<String,Object> map ;
        @IgniteInstanceResource
        private Ignite ignite;

        @LoggerResource
        private IgniteLogger log;
        public CustIgniteCallable(Map<String, Object> map) {
            this.map = map;
        }

        @Override
        public Boolean call() throws Exception {
            return deal(map,ignite,log);
        }


    }

    class CustIgniteRunnable implements IgniteRunnable{
        private Map<String,Object> map ;
        @IgniteInstanceResource
        private Ignite ignite;
        @LoggerResource
        private IgniteLogger log;
        public CustIgniteRunnable(Map<String, Object> map) {
            this.map = map;
        }
        @Override
        public void run() {
            System.out.println(deal(map,ignite,log));
        }
    }

    public static boolean deal(Map<String,Object> map,Ignite ignite,IgniteLogger log) {
        try {
            String cacheName = (String)map.get("cachename") ;
            Map<UUID,Integer> indexMap = (Map<UUID,Integer>)map.get("indexMap") ;
            UUID uuid = (UUID)ignite.cluster().localNode().consistentId() ;
            Integer index = indexMap.get(uuid) ;
            log.info("\r\n---------cachename:"+cacheName+"--"+index+"\r\n");
            IgniteCache<String,PartitionCustObj> igniteCache = ignite.cache(cacheName) ;
            String key = UUID.randomUUID().toString() ;
            igniteCache.put(key,new PartitionCustObj(key,key));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("---------Exception:"+e.getMessage());
        }
        return false;
    }
}
