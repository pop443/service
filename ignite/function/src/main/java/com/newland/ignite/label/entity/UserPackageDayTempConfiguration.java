package com.newland.ignite.label.entity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class UserPackageDayTempConfiguration extends CustCacheConfiguration<String,UserPackageDayTemp> {
    public UserPackageDayTempConfiguration() {
        super(String.class, UserPackageDayTemp.class);
    }

    @Override
    public CacheConfiguration<String, UserPackageDayTemp> getCacheConfiguration() {
        return super.getCacheConfiguration();
    }
}
