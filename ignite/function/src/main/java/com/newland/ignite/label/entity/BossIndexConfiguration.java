package com.newland.ignite.label.entity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class BossIndexConfiguration extends CustCacheConfiguration<String,BossIndex> {
    public BossIndexConfiguration() {
        super(String.class, BossIndex.class);
    }

    @Override
    public CacheConfiguration<String, BossIndex> getCacheConfiguration() {
        CacheConfiguration<String, BossIndex> cfg = super.getCacheConfiguration();
        cfg.setCacheMode(CacheMode.PARTITIONED) ;
        cfg.setBackups(0);
        return cfg ;
    }
}
