package com.newland.ignite.affinity;

import com.newland.ignite.affinity.entity.AffinityDemo;
import com.newland.ignite.affinity.entity.AffinityDemoConfiguration;
import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.AutomationConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cluster.ClusterNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by xz on 2020/6/18.
 */
public class AffinityKeyUtil {
    private Ignite ignite ;
    private AffinityDemoConfiguration cfg = null ;
    private IgniteCache<String,AffinityDemo> igniteCache = null ;
    @Before
    public void Before(){
        ignite = IgniteUtil.getIgnite();
        cfg = new AffinityDemoConfiguration() ;
        igniteCache = cfg.getIgniteCache(ignite) ;
    }
    @Test
    public void insert(){
        for (int i = 0; i < 100; i++) {

        }
    }

    @Test
    public void distributed(){
        String key = "100" ;
        Affinity affinityPerson = ignite.affinity("EXPIRY") ;
        Collection<ClusterNode> collection = affinityPerson.mapKeyToPrimaryAndBackups(key) ;
        for (ClusterNode clusterNode:collection) {
            String ip = (String)clusterNode.attribute("org.apache.ignite.ips");
            if (affinityPerson.isPrimary(clusterNode,key)){
                System.out.println("主节点"+ip);
            }else{
                System.out.println("backs节点"+ip);
            }
        }
    }



    @After
    public void After(){
        IgniteUtil.release(ignite);
    }
}
