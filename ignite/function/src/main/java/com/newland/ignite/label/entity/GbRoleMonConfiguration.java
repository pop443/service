package com.newland.ignite.label.entity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class GbRoleMonConfiguration extends CustCacheConfiguration<String,GbRoleMon> {
    public GbRoleMonConfiguration() {
        super(String.class, GbRoleMon.class);
    }

    @Override
    public CacheConfiguration<String, GbRoleMon> getCacheConfiguration() {
        CacheConfiguration<String, GbRoleMon> cfg = super.getCacheConfiguration();
        cfg.setCacheMode(CacheMode.PARTITIONED) ;
        return cfg;
    }
}
