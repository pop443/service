package com.newland.ignite.secondary.entity;

import com.newland.ignite.entryprocessor.entity.Temp;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;

import java.util.UUID;

/**
 * Created by xz on 2020/3/6.
 */
public class StudentConfiguration extends CustCacheConfiguration<String,Student> {
    public StudentConfiguration() {
        super(String.class, Student.class);
    }

    @Override
    public CacheConfiguration<String, Student> getCacheConfiguration() {
        CacheConfiguration<String, Student> cachecfg = super.getCacheConfiguration() ;
        cachecfg.setStatisticsEnabled(true) ;
        return cachecfg;
    }
}
