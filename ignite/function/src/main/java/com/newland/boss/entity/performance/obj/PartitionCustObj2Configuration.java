package com.newland.boss.entity.performance.obj;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class PartitionCustObj2Configuration extends CustCacheConfiguration<String,PartitionCustObj2> {
    public PartitionCustObj2Configuration() {
        super(String.class, PartitionCustObj2.class,0);
    }
    public PartitionCustObj2Configuration(int backups) {
        super(String.class, PartitionCustObj2.class,backups);
    }

    @Override
    public CacheConfiguration<String, PartitionCustObj2> getCacheConfiguration() {
        CacheConfiguration<String, PartitionCustObj2> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(backups);
        return cacheConfiguration;
    }
}
