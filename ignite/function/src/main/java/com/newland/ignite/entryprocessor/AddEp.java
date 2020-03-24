package com.newland.ignite.entryprocessor;

import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheEntryProcessor;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

/**
 * Created by xz on 2020/3/24.
 */
public class AddEp implements CacheEntryProcessor<String,BinaryObject,Void> {
    @Override
    public Void process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
        int addCount = (int)objects[0];
        BinaryObject oldValue = mutableEntry.getValue() ;
        int oldCount = oldValue.field("count");
        int newCount = addCount+oldCount ;
        BinaryObject newValue = oldValue.toBuilder().setField("count",newCount).build() ;
        mutableEntry.setValue(newValue);
        System.out.println("增加："+addCount+",原值："+oldCount+",新值:"+newCount);
        return null;
    }
}
