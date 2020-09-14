package com.newland.ignite.multicenter.eventlinsten;

import org.apache.ignite.binary.BinaryObject;

import javax.cache.event.*;
import java.util.Iterator;

/**
 * Created by xz on 2020/9/10.
 */
public class CustCacheEntryListener implements CacheEntryCreatedListener<String, BinaryObject>, CacheEntryUpdatedListener<String, BinaryObject>, CacheEntryRemovedListener<String, BinaryObject>, CacheEntryExpiredListener<String, BinaryObject> {

    @Override
    public void onCreated(Iterable<CacheEntryEvent<? extends String, ? extends BinaryObject>> iterable) throws CacheEntryListenerException {
        print(iterable, "onCreated");
    }

    @Override
    public void onExpired(Iterable<CacheEntryEvent<? extends String, ? extends BinaryObject>> iterable) throws CacheEntryListenerException {
        print(iterable, "onExpired");
    }

    @Override
    public void onRemoved(Iterable<CacheEntryEvent<? extends String, ? extends BinaryObject>> iterable) throws CacheEntryListenerException {
        print(iterable, "onRemoved");
    }

    @Override
    public void onUpdated(Iterable<CacheEntryEvent<? extends String, ? extends BinaryObject>> iterable) throws CacheEntryListenerException {
        print(iterable, "onUpdated");
    }

    private void print(Iterable<CacheEntryEvent<? extends String, ? extends BinaryObject>> iterable, String name) {
        Iterator<CacheEntryEvent<? extends String, ? extends BinaryObject>> it = iterable.iterator();
        while (it.hasNext()) {
            CacheEntryEvent<? extends String, ? extends BinaryObject> entryEvent = it.next();
            String key = entryEvent.getKey();
            BinaryObject value = entryEvent.getValue();
            System.out.println(name + "---key:" + key + "-- value:" + value);
        }
    }
}
