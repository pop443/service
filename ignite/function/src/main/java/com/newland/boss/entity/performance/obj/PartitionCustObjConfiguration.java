package com.newland.boss.entity.performance.obj;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class PartitionCustObjConfiguration extends CustCacheConfiguration<String,PartitionCustObj> {
    public PartitionCustObjConfiguration() {
        super(String.class, PartitionCustObj.class,0);
    }

    public PartitionCustObjConfiguration(int backups) {
        super(String.class, PartitionCustObj.class,backups);
    }

    public PartitionCustObjConfiguration(CacheRebalanceMode cacheRebalanceMode) {
        super(String.class, PartitionCustObj.class,cacheRebalanceMode);
    }

    @Override
    public CacheConfiguration<String, PartitionCustObj> getCacheConfiguration() {
        CacheConfiguration<String, PartitionCustObj> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC) ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setBackups(backups);
        return cacheConfiguration;
    }
}
