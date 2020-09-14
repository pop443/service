package com.newland.boss.entity.keylikequery;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class AtomicQueryConfiguration extends CustCacheConfiguration<String,AffinityItemNo> {
    public AtomicQueryConfiguration() {
        super(String.class, AffinityItemNo.class);
    }

    @Override
    public CacheConfiguration<String, AffinityItemNo> getCacheConfiguration() {
        CacheConfiguration<String, AffinityItemNo> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC) ;
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
