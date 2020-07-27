package com.newland.ignite.affinitydata;

import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/3/6.
 */
public class PersonConfiguration extends CustCacheConfiguration<PersonKey,Person> {
    public PersonConfiguration() {
        super(PersonKey.class, Person.class);
    }

    @Override
    public CacheConfiguration<PersonKey, Person> getCacheConfiguration() {
        CacheConfiguration<PersonKey, Person> cfg = super.getCacheConfiguration() ;
        cfg.setCacheMode(CacheMode.PARTITIONED) ;
        cfg.setBackups(2) ;
        return cfg;
    }
}
