package com.newland.ignite.multicenter.eventlinsten;

import org.apache.ignite.binary.BinaryObject;

import javax.cache.event.*;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by xz on 2020/9/10.
 */
public class CustCacheEntryListener2<K, V> implements CacheEntryCreatedListener<K, V>, CacheEntryUpdatedListener<K, V>, CacheEntryRemovedListener<K, V>, CacheEntryExpiredListener<K, V>,Serializable {

    @Override
    public void onCreated(Iterable<CacheEntryEvent<? extends K, ? extends V>> iterable) throws CacheEntryListenerException {
        print(iterable, "onCreated");
    }

    @Override
    public void onExpired(Iterable<CacheEntryEvent<? extends K, ? extends V>> iterable) throws CacheEntryListenerException {
        print(iterable, "onExpired");
    }

    @Override
    public void onRemoved(Iterable<CacheEntryEvent<? extends K, ? extends V>> iterable) throws CacheEntryListenerException {
        print(iterable, "onRemoved");
    }

    @Override
    public void onUpdated(Iterable<CacheEntryEvent<? extends K, ? extends V>> iterable) throws CacheEntryListenerException {
        print(iterable, "onUpdated");
    }

    private void print(Iterable<CacheEntryEvent<? extends K, ? extends V>> iterable, String name) {
        Iterator<CacheEntryEvent<? extends K, ? extends V>> it = iterable.iterator();
        while (it.hasNext()) {
            CacheEntryEvent<? extends K, ? extends V> entryEvent = it.next();
            K key = entryEvent.getKey();
            V value = entryEvent.getValue();
            System.out.println(name + "---key:" + key + "-- value:" + value);
        }
    }
}
