package com.newland.ignite.affinitydata;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class CompanyConfiguration extends CustCacheConfiguration<String,Company> {
    public CompanyConfiguration() {
        super(String.class, Company.class);
    }

    @Override
    public CacheConfiguration<String, Company> getCacheConfiguration() {
        return super.getCacheConfiguration();
    }
}
