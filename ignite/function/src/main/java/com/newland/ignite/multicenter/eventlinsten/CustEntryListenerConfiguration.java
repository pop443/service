package com.newland.ignite.multicenter.eventlinsten;

import org.apache.ignite.binary.BinaryObject;

import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListener;

/**
 * Created by xz on 2020/9/10.
 */
public class CustEntryListenerConfiguration<K,V> implements CacheEntryListenerConfiguration<K, V> {
    @Override
    public Factory<CacheEntryListener<? super K, ? super V>> getCacheEntryListenerFactory() {
        return new Factory<CacheEntryListener<? super K, ? super V>>() {
            @Override
            public CacheEntryListener<? super K, ? super V> create() {
                return new CustCacheEntryListener2<>();
            }
        };
    }

    @Override
    public boolean isOldValueRequired() {
        return false;
    }

    @Override
    public Factory<CacheEntryEventFilter<? super K, ? super V>> getCacheEntryEventFilterFactory() {
        return null;
    }

    @Override
    public boolean isSynchronous() {
        return false;
    }
}
