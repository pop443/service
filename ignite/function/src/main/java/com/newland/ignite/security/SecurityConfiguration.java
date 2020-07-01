package com.newland.ignite.security;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class SecurityConfiguration extends CustCacheConfiguration<String,Security> {
    public SecurityConfiguration() {
        super(String.class, Security.class);
    }

    @Override
    public CacheConfiguration<String, Security> getCacheConfiguration() {
        CacheConfiguration<String, Security> cfg = super.getCacheConfiguration() ;
        cfg.setCacheMode(CacheMode.PARTITIONED) ;
        cfg.setBackups(2) ;
        cfg.setEncryptionEnabled(true);
        return cfg;
    }
}
