package com.newland.ignite.cachestore.entity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class AutomationConfiguration extends CustCacheConfiguration<String,Automation> {
    public AutomationConfiguration() {
        super(String.class, Automation.class);
    }

    @Override
    public CacheConfiguration<String, Automation> getCacheConfiguration() {
        CacheConfiguration<String, Automation> cachecfg = super.getCacheConfiguration() ;
        return cachecfg;
    }
}
