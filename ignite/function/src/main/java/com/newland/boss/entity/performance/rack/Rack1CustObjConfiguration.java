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
public class Rack1CustObjConfiguration extends CustCacheConfiguration<String, Rack1CustObj> {
    public Rack1CustObjConfiguration() {
        super(String.class, Rack1CustObj.class,2);
    }
    public Rack1CustObjConfiguration(int backup) {
        super(String.class, Rack1CustObj.class,backup);
    }
    @Override
    public CacheConfiguration<String, Rack1CustObj> getCacheConfiguration() {
        CacheConfiguration<String, Rack1CustObj> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC) ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(backups);
        RackawareAffinityFunction affinityFunction = new RackawareAffinityFunction(2048,false) ;
        cacheConfiguration.setAffinity(affinityFunction);
        return cacheConfiguration;
    }
}
