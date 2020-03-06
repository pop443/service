package com.newland.ignite.entryprocessor;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.resources.LoggerResource;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

/**
 * Created by Administrator on 2020/1/3.
 */
public class TestEP implements CacheEntryProcessor<String,String,Boolean>{

    private static final long serialVersionUID = 123L;
    @LoggerResource
    private IgniteLogger log;
    @Override
    public Boolean process(MutableEntry<String, String> mutableEntry, Object... objects) throws EntryProcessorException {
        Boolean bo = false ;
        String value = (String)objects[0] ;
        try {
            mutableEntry.setValue(value);
            bo = true ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("---------------TestEP 1--------"+value);
        return bo;
    }
}
