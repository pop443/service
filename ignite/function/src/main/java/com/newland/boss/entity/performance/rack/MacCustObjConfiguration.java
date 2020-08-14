package com.newland.boss.entity.performance.rack;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.affinity.rendezvous.RendezvousAffinityFunction;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class MacCustObjConfiguration extends CustCacheConfiguration<String, MacCustObj> {
    public MacCustObjConfiguration() {
        super(String.class, MacCustObj.class,2);
    }
    public MacCustObjConfiguration(int backup) {
        super(String.class, MacCustObj.class,backup);
    }

    @Override
    public CacheConfiguration<String, MacCustObj> getCacheConfiguration() {
        CacheConfiguration<String, MacCustObj> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC) ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(backups);
        RendezvousAffinityFunction affinityFunction = new RendezvousAffinityFunction(true,2048) ;
        cacheConfiguration.setAffinity(affinityFunction);
        return cacheConfiguration;
    }
}
