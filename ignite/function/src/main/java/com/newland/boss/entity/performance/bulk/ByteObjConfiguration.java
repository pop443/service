package com.newland.boss.entity.performance.bulk;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/16.
 */
public class ByteObjConfiguration extends CustCacheConfiguration<String, byte[]> {
    public ByteObjConfiguration() {
        super(String.class, byte[].class);
    }

    @Override
    public CacheConfiguration<String, byte[]> getCacheConfiguration() {
        CacheConfiguration<String, byte[]> cacheConfiguration = super.getCacheConfiguration();
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
