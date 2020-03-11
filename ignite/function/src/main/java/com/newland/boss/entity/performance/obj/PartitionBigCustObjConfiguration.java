package com.newland.boss.entity.performance.obj;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class PartitionBigCustObjConfiguration extends CustCacheConfiguration<String,PartitionBigCustObj> {
    public PartitionBigCustObjConfiguration() {
        super(String.class, PartitionBigCustObj.class);
    }

    @Override
    public CacheConfiguration<String, PartitionBigCustObj> getCacheConfiguration() {
        CacheConfiguration<String, PartitionBigCustObj> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
