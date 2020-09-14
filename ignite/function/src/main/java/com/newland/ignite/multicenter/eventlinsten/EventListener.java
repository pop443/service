package com.newland.ignite.multicenter.eventlinsten;

import com.newland.ignite.multicenter.entity.MultiCenterObj;
import com.newland.ignite.multicenter.entity.MultiCenterObjConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xz on 2020/9/10.
 */
public class EventListener {

    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,MultiCenterObj> cacheConfiguration = new MultiCenterObjConfiguration().getCacheConfiguration() ;
        IgniteCache<String,MultiCenterObj> igniteCache = ignite.getOrCreateCache(cacheConfiguration) ;
        IgniteCache<String,BinaryObject> ic = igniteCache.withKeepBinary() ;
        ic.registerCacheEntryListener(new CustEntryListenerConfiguration());
    }

}
