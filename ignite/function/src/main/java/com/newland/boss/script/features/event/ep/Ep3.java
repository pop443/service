package com.newland.boss.script.features.event.ep;

import com.newland.ignite.cachestore.entity.Expiry;
import org.apache.ignite.cache.CacheEntryProcessor;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

/**
 * Created by xz on 2020/5/19.
 */
public class Ep3 implements CacheEntryProcessor<String,Expiry,Expiry> {
    @Override
    public Expiry process(MutableEntry<String, Expiry> mutableEntry, Object... objects) throws EntryProcessorException {
        return null;
    }
}
