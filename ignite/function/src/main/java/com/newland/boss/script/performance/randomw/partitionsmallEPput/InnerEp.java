package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.binary.BinaryObjectException;
import org.apache.ignite.resources.CacheNameResource;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.transactions.TransactionException;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

/**
 * Created by xz on 2020/8/25.
 */
public class InnerEp implements EntryProcessor<String,BinaryObject,Boolean> {

    @IgniteInstanceResource
    private Ignite ignite;
    private IgniteCache<String,BinaryObject> cache2 ;

    public InnerEp(IgniteCache<String, BinaryObject> cache2) {
        this.cache2 = cache2;
    }

    @Override
    public Boolean process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
        Boolean bo = false ;
        try {
            BinaryObject binaryObject1 = (BinaryObject)objects[0] ;
            BinaryObject binaryObject2 = (BinaryObject)objects[1] ;
            mutableEntry.setValue(binaryObject1);
            cache2.put(binaryObject2.field("id"),binaryObject2);
            bo = true ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bo;
    }
}
