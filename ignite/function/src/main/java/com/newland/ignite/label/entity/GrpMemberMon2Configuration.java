package com.newland.ignite.label.entity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class GrpMemberMon2Configuration extends CustCacheConfiguration<String,GrpMemberMon2> {
    public GrpMemberMon2Configuration() {
        super(String.class, GrpMemberMon2.class);
    }

    @Override
    public CacheConfiguration<String, GrpMemberMon2> getCacheConfiguration() {
        return super.getCacheConfiguration();
    }
}
