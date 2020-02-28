package com.xz.ignite.basefunction.spring.entity.cust;

import com.xz.ignite.basefunction.spring.entity.BaseCacheConfig;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/2/27.
 */
@Configuration
public class Spring1EntityCacheConfig extends BaseCacheConfig<Integer,Spring1Entity> {
    public Spring1EntityCacheConfig() {
        super(Integer.class, Spring1Entity.class);
    }

    @Bean(name = "Spring1EntityCacheConfig")
    @Override
    public CacheConfiguration<Integer, Spring1Entity> getCacheConfig() {
        return super.getCacheConfig();
    }
}
