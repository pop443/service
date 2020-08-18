package com.newland.ignite.entryprocessor;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.resources.LoggerResource;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

/**
 * Created by xz on 2020/8/18.
 */
public class LongSleepEp implements CacheEntryProcessor<String,BinaryObject,Boolean> {
    @LoggerResource
    private IgniteLogger igniteLogger ;

    @Override
    public Boolean process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
        BinaryObject binaryObject = (BinaryObject)objects[0] ;
        mutableEntry.setValue(binaryObject);
        try {
            igniteLogger.info("-----------------start to sleep");
            Thread.sleep(3*60*1000);
            return true ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
