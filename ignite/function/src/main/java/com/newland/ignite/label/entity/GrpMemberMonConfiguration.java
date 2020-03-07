package com.newland.ignite.label.entity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class GrpMemberMonConfiguration extends CustCacheConfiguration<String,GrpMemberMon> {
    public GrpMemberMonConfiguration() {
        super(String.class, GrpMemberMon.class);
    }

    @Override
    public CacheConfiguration<String, GrpMemberMon> getCacheConfiguration() {
        return super.getCacheConfiguration();
    }
}
