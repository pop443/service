package com.newland.ignite.entryprocessor;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.resources.LoggerResource;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

/**
 * Created by xz on 2020/3/24.
 */
public class NoopEp implements CacheEntryProcessor<String,BinaryObject,Object> {

    @LoggerResource
    private IgniteLogger igniteLogger ;

    @Override
    public Object process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {

        igniteLogger.info("----------------\r\n----------------\r\n----------------");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--------------1----------------");
        return null;
    }
}
