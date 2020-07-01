package com.newland.ignite.affinity.entity;

import com.newland.ignite.affinitydata.Company;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class AffinityDemoConfiguration extends CustCacheConfiguration<String,AffinityDemo> {
    public AffinityDemoConfiguration() {
        super(String.class, AffinityDemo.class);
    }

    @Override
    public CacheConfiguration<String, AffinityDemo> getCacheConfiguration() {
        CacheConfiguration<String, AffinityDemo> cfg = super.getCacheConfiguration() ;
        cfg.setCacheMode(CacheMode.PARTITIONED) ;
        cfg.setBackups(1);
        return cfg;
    }
}
