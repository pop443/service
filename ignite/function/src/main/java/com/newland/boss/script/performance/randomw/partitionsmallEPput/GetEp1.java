package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import org.apache.ignite.binary.BinaryObject;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.util.Map;

/**
 * Created by xz on 2020/3/16.
 */
public class GetEp1 implements EntryProcessor<String, BinaryObject, BinaryObject> {
    @Override
    public BinaryObject process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
        return mutableEntry.getValue();
    }
}
