package com.newland.ignite.multicenter;

import com.newland.ignite.multicenter.entity.MultiCenterObj;
import com.newland.ignite.multicenter.entity.MultiCenterObjConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xz on 2020/8/28.
 */
public class MultiCenterObjTest {
    private Ignite ignite ;
    private IgniteCache<String,MultiCenterObj> igniteCache ;
    private IgniteDataStreamer<String,MultiCenterObj> ids ;
    private String cacheName ;
    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,MultiCenterObj> cacheConfiguration = new MultiCenterObjConfiguration().getCacheConfiguration() ;
        cacheName = cacheConfiguration.getName() ;
        igniteCache = ignite.getOrCreateCache(cacheConfiguration) ;
    }
    @Test
    public void test(){
        int index = 3 ;

        Map<String,MultiCenterObj> map = new LinkedHashMap<>() ;
        for (int i = 0; i < index; i++) {
            String key = i+"" ;
            map.put(key,new MultiCenterObj(key,i));
        }
        igniteCache.putAll(map);
        igniteCache.remove("0");
        igniteCache.remove("1");
        igniteCache.remove("2");
        for (int i = 0; i < index; i++) {
            String key = i+"hello" ;
            igniteCache.put(key,new MultiCenterObj(key,i));
        }




        /*IgniteCache<String,BinaryObject> ic = igniteCache.withKeepBinary() ;


        for (int i = 0; i < index; i++) {
            String key = "hello"+"" ;
            BinaryObject binaryObject = IgniteUtil.toBinary(new MultiCenterObj(key,i)) ;
            Boolean bo = ic.invoke(binaryObject.field("id"),new PutEp1(),binaryObject);
            System.out.println(bo);
        }
        Affinity affinity = ignite.affinity(cacheName) ;
        print(affinity,"0");
        print(affinity,"1");*/


        //ids.keepBinary(true);
        //ids.addData(map) ;
        //ids.flush();
        //ids.close();
    }

    private void print(Affinity affinity,String key) {
        Collection<ClusterNode> collection = affinity.mapKeyToPrimaryAndBackups(key) ;
        for (ClusterNode clusterNode:collection) {
            if (affinity.isPrimary(clusterNode,key)){
                System.out.println((String)clusterNode.attribute("org.apache.ignite.ips"));
            }
        }
    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
