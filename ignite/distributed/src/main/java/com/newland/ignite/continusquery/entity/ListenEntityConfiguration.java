package com.newland.ignite.continusquery.entity;

import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class ListenEntityConfiguration extends CustCacheConfiguration<String,ListenEntity> {
    public ListenEntityConfiguration() {
        super(String.class, ListenEntity.class);
    }

    @Override
    public CacheConfiguration<String, ListenEntity> getCacheConfiguration() {
        CacheConfiguration<String, ListenEntity> cachecfg = super.getCacheConfiguration() ;
        cachecfg.setPartitionLossPolicy(PartitionLossPolicy.READ_ONLY_SAFE);
        return cachecfg;
    }
}
