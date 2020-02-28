package com.xz.ignite.basefunction.spring.entity.cust;

import com.xz.ignite.basefunction.spring.entity.BaseCacheConfig;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/2/27.
 */
public class Spring2EntityCacheConfig extends BaseCacheConfig<String,Spring2Entity> {
    public Spring2EntityCacheConfig() {
        super(String.class, Spring2Entity.class);
    }
}
