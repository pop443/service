package com.newland.ignite.label.entity;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class GrpCrmCustMonConfiguration extends CustCacheConfiguration<String,GrpCrmCustMon> {
    public GrpCrmCustMonConfiguration() {
        super(String.class, GrpCrmCustMon.class);
    }

    @Override
    public CacheConfiguration<String, GrpCrmCustMon> getCacheConfiguration() {
        return super.getCacheConfiguration();
    }
}
