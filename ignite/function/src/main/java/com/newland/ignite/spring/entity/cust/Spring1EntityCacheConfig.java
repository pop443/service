package com.newland.ignite.spring.entity.cust;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/2/27.
 */
@Configuration
public class Spring1EntityCacheConfig extends CustCacheConfiguration<Integer,SpringEntity> {
    public Spring1EntityCacheConfig() {
        super(Integer.class, SpringEntity.class);
    }

    @Bean(name = "Spring1EntityCacheConfig")
    @Override
    public CacheConfiguration<Integer, SpringEntity> getCacheConfiguration() {
        return super.getCacheConfiguration();
    }
}
