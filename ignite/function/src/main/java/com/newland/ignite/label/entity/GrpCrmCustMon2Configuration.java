package com.newland.ignite.label.entity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class GrpCrmCustMon2Configuration extends CustCacheConfiguration<String,GrpCrmCustMon2> {
    public GrpCrmCustMon2Configuration() {
        super(String.class, GrpCrmCustMon2.class);
    }

    @Override
    public CacheConfiguration<String, GrpCrmCustMon2> getCacheConfiguration() {
        return super.getCacheConfiguration();
    }
}
