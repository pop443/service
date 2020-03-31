package com.newland.ignite.structure;

import com.newland.ignite.affinity.Person;
import com.newland.ignite.affinity.PersonKey;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class ChangeDataConfiguration extends CustCacheConfiguration<String,ChangeData> {
    public ChangeDataConfiguration() {
        super(String.class, ChangeData.class);
    }

    @Override
    public CacheConfiguration<String, ChangeData> getCacheConfiguration() {
        CacheConfiguration<String, ChangeData> cfg = super.getCacheConfiguration();
        cfg.setCacheMode(CacheMode.PARTITIONED) ;
        return super.getCacheConfiguration();
    }
}
