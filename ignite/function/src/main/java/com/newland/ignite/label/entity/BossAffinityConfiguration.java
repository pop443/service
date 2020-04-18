package com.newland.ignite.label.entity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class BossAffinityConfiguration extends CustCacheConfiguration<BossAffinityKey,BossAffinity> {
    public BossAffinityConfiguration() {
        super(BossAffinityKey.class, BossAffinity.class);
    }

    @Override
    public CacheConfiguration<BossAffinityKey,BossAffinity> getCacheConfiguration() {
        CacheConfiguration<BossAffinityKey,BossAffinity> cfg = super.getCacheConfiguration();
        cfg.setCacheMode(CacheMode.PARTITIONED) ;
        cfg.setBackups(0);
        return cfg ;
    }
}
