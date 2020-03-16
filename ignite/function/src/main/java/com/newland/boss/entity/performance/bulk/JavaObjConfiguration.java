package com.newland.boss.entity.performance.bulk;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/16.
 */
public class JavaObjConfiguration extends CustCacheConfiguration<String, JavaObj> {
    public JavaObjConfiguration() {
        super(String.class, JavaObj.class);
    }

    @Override
    public CacheConfiguration<String, JavaObj> getCacheConfiguration() {
        CacheConfiguration<String, JavaObj> cacheConfiguration = super.getCacheConfiguration();
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
