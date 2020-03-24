package com.newland.ignite.entryprocessor;

import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheEntryProcessor;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

/**
 * Created by xz on 2020/3/24.
 */
public class NoopEp implements CacheEntryProcessor<String,BinaryObject,Object> {
    @Override
    public Object process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
        System.out.println("--------------1----------------");
        return null;
    }
}
