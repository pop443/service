package com.newland.ignite.spring.entity.cust;

import com.newland.ignite.spring.entity.BaseCacheConfig;

/**
 * Created by xz on 2020/2/27.
 */
public class Spring2EntityCacheConfig extends BaseCacheConfig<String,Spring2Entity> {
    public Spring2EntityCacheConfig() {
        super(String.class, Spring2Entity.class);
    }
}
