package com.newland.boss.entity.performance.complex;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class ComplexConfiguration extends CustCacheConfiguration<ComplexKey,ComplexValue> {
    public ComplexConfiguration() {
        super(ComplexKey.class, ComplexValue.class);
    }

    @Override
    public CacheConfiguration<ComplexKey,ComplexValue> getCacheConfiguration() {
        CacheConfiguration<ComplexKey,ComplexValue> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL) ;
        cacheConfiguration.setBackups(0);
        return cacheConfiguration;
    }
}
