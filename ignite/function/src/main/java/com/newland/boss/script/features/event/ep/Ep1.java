package com.newland.boss.script.features.event.ep;

import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.Expiry;
import org.apache.ignite.cache.CacheEntryProcessor;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

/**
 * Created by xz on 2020/5/19.
 */
public class Ep1 implements CacheEntryProcessor<String,Expiry,Expiry> {
    private String name ;
    private Automation automation ;

    public Ep1(String name, Automation automation) {
        this.name = name;
        this.automation = automation;
    }

    @Override
    public Expiry process(MutableEntry<String, Expiry> mutableEntry, Object... objects) throws EntryProcessorException {
        System.out.println("Ep1--------------"+name);
        System.out.println("Ep1--------------"+automation);
        return mutableEntry.getValue();
    }
}