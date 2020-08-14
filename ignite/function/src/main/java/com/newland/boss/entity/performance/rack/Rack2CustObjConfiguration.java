package com.newland.boss.entity.performance.rack;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.affinity.rackaware.RackawareAffinityFunction;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class Rack2CustObjConfiguration extends CustCacheConfiguration<String, Rack2CustObj> {
    public Rack2CustObjConfiguration() {
        super(String.class, Rack2CustObj.class,2);
    }
    public Rack2CustObjConfiguration(int backup) {
        super(String.class, Rack2CustObj.class,backup);
    }
    @Override
    public CacheConfiguration<String, Rack2CustObj> getCacheConfiguration() {
        CacheConfiguration<String, Rack2CustObj> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC) ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(backups);
        RackawareAffinityFunction affinityFunction = new RackawareAffinityFunction(2048,true) ;
        cacheConfiguration.setAffinity(affinityFunction);
        return cacheConfiguration;
    }
}
