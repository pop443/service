package com.newland.boss.problem.produce;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2020/6/2.
 */
public class BplusConfiguration extends CustCacheConfiguration<String,Bplus> {
    public BplusConfiguration() {
        super(String.class, Bplus.class);
    }
    @Override
    public CacheConfiguration<String, Bplus> getCacheConfiguration() {
        CacheConfiguration<String, Bplus> cachecfg = super.getCacheConfiguration() ;
        cachecfg.setCacheMode(CacheMode.PARTITIONED) ;
        cachecfg.setBackups(0);
        cachecfg.setAtomicityMode(CacheAtomicityMode.ATOMIC) ;

        cachecfg.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 10L)));

        return cachecfg;
    }
}
