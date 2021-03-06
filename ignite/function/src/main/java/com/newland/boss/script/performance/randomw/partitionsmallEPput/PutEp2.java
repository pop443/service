package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import org.apache.ignite.binary.BinaryObject;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.util.Map;

/**
 * Created by xz on 2020/3/16.
 */
public class PutEp2 implements EntryProcessor<String, BinaryObject, Boolean> {
    @Override
    public Boolean process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
        boolean bo = false;
        try {
            Map<String,BinaryObject> map = (Map<String,BinaryObject>)objects[0] ;
            BinaryObject binaryObject = map.get(mutableEntry.getKey());
            mutableEntry.setValue(binaryObject);
            bo = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bo;
    }
}
