package com.newland.boss.entity.user;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/3/2.
 */
@Configuration
public class UserConfiguration extends CustCacheConfiguration<String,User> {
    public UserConfiguration() {
        super(String.class, User.class);
    }

    @Override
    public CacheConfiguration<String, User> getCacheConfiguration() {
        CacheConfiguration<String, User> cacheConfiguration = super.getCacheConfiguration() ;
        cacheConfiguration.setBackups(1);
        return cacheConfiguration;
    }
}
