package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.binary.BinaryObjectBuilder;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

/**
 * Created by xz on 2020/3/16.
 */
public class PutEp1 implements EntryProcessor<String, BinaryObject, Boolean> {
    @Override
    public Boolean process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
        boolean bo = false;
        try {
            BinaryObject binaryObject = (BinaryObject) objects[0];
            BinaryObjectBuilder builder = binaryObject.toBuilder() ;
            builder.setField("1","1") ;
            BinaryObject binaryObject2 = builder.build() ;
            mutableEntry.setValue(binaryObject);
            bo = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bo;
    }
}
