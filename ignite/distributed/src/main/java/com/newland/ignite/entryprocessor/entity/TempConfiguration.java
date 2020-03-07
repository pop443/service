package com.newland.ignite.entryprocessor.entity;

import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

import java.util.UUID;

/**
 * Created by xz on 2020/3/6.
 */
public class TempConfiguration extends CustCacheConfiguration<UUID,Temp> {
    public TempConfiguration() {
        super(UUID.class, Temp.class);
    }

    @Override
    public CacheConfiguration<UUID, Temp> getCacheConfiguration() {
        CacheConfiguration<UUID, Temp> cachecfg = super.getCacheConfiguration() ;
        return cachecfg;
    }
}
