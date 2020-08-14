package com.newland.ignite.rack;

import com.newland.boss.entity.performance.rack.Rack1CustObj;
import com.newland.boss.entity.performance.rack.Rack1CustObjConfiguration;
import com.newland.boss.entity.performance.rack.Rack2CustObj;
import com.newland.boss.entity.performance.rack.Rack2CustObjConfiguration;
import com.newland.ignite.cachestore.entity.Course;
import com.newland.ignite.cachestore.entity.CourseConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cache.affinity.rackaware.RackAwareAdapt;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.IgniteNodeAttributes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.util.Collection;
import java.util.concurrent.locks.Lock;

/**
 * Created by xz on 2020/2/9.
 */
public class RackTest {
    private Ignite ignite = null ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    /**
     * 正常机架
     * 第一次
     * rack1
     *      243 node1
     * rack2
     *      244 node1 node2
     * rack3
     *      245 node1
     * 第二次
     * rack1
     *      243 node1
     * rack2
     *      244 node1 node2
     * rack2
     *      245 node1
     */
    @Test
    public void rackErrorStep1(){
        Rack2CustObjConfiguration cfg = new Rack2CustObjConfiguration(2) ;
        IgniteCache<String,Rack2CustObj> igniteCache = ignite.getOrCreateCache(cfg.getCacheConfiguration()) ;
        for (int i = 0; i < 10; i++) {
            int index = i ;
            igniteCache.put(index+"",new Rack2CustObj("1","1"));
            Affinity affinity = ignite.affinity(cfg.getCacheConfiguration().getName()) ;
            printPartition(affinity,index,index+1);
        }

    }

    @Test
    public void rackErrorStep2(){
        Rack2CustObjConfiguration cfg = new Rack2CustObjConfiguration(2) ;
        Affinity affinity = ignite.affinity(cfg.getCacheConfiguration().getName()) ;
        printPartition(affinity,0,10);
    }

    @Test
    public void rackErrorStep3(){
        Rack2CustObjConfiguration cfg = new Rack2CustObjConfiguration(2) ;
        IgniteCache<String,Rack2CustObj> igniteCache = ignite.getOrCreateCache(cfg.getCacheConfiguration()) ;
        for (int i = 0; i < 10; i++) {
            int index = i ;
            Rack2CustObj rack2CustObj = igniteCache.get(index+"");
            System.out.println(rack2CustObj);
        }
    }


    /**
     * 机架感知之主机机架
     */
    @Test
    public void rackNotSimple(){
        Affinity affinity = ignite.affinity(Rack1CustObj.class.getSimpleName().toUpperCase()) ;
        printPartition(affinity,1999,3000);
    }

    @Test
    public void rackSimple(){
        Affinity affinity = ignite.affinity(Rack2CustObj.class.getSimpleName().toUpperCase()) ;
        printPartition(affinity,1999,3000);
    }

    private void printPartition(Affinity affinity,int min,int max) {
        for (int i = min; i < max; i++) {
            String key = i+"" ;
            Collection<ClusterNode> collection = affinity.mapKeyToPrimaryAndBackups(key) ;
            System.out.print(key+":");
            collection.forEach(clusterNode -> {
                if (affinity.isPrimary(clusterNode,key)){
                    System.out.print("主"+clusterNode.attribute(RackAwareAdapt.ATTR_RACK)+"\t"+clusterNode.order()+"\t"+clusterNode.attribute(IgniteNodeAttributes.ATTR_IPS)+"\t\t\t");
                }
            });
            collection.forEach(clusterNode -> {
                if (!affinity.isPrimary(clusterNode,key)){
                    System.out.print("备"+clusterNode.attribute(RackAwareAdapt.ATTR_RACK)+"\t"+clusterNode.order()+"\t"+clusterNode.attribute(IgniteNodeAttributes.ATTR_IPS)+"\t\t\t");
                }
            });
            System.out.println();
        }
    }


    @After
    public void after(){
        if (ignite!=null){
            IgniteUtil.release(ignite);
        }
    }

}
