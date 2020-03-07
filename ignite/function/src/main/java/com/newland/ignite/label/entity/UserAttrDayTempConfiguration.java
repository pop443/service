package com.newland.ignite.label.entity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class UserAttrDayTempConfiguration extends CustCacheConfiguration<String,UserAttrDayTemp> {
    public UserAttrDayTempConfiguration() {
        super(String.class, UserAttrDayTemp.class);
    }

    @Override
    public CacheConfiguration<String, UserAttrDayTemp> getCacheConfiguration() {
        return super.getCacheConfiguration();
    }
}
