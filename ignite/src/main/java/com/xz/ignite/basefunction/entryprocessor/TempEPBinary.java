package com.xz.ignite.basefunction.entryprocessor;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.binary.BinaryObjectBuilder;
import org.apache.ignite.binary.BinaryObjectException;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.resources.LoggerResource;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.util.UUID;

/**
 * Created by Administrator on 2020/1/3.
 */
public class TempEPBinary implements CacheEntryProcessor<UUID,BinaryObject,Boolean>{

    @LoggerResource
    private IgniteLogger log;

    @Override
    public Boolean process(MutableEntry<UUID, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
        Boolean bo = false ;
        try {
            String value = (String)objects[0] ;

            BinaryObject binaryObject = mutableEntry.getValue() ;
            BinaryObjectBuilder binaryObjectBuilder = binaryObject.toBuilder() ;
            binaryObjectBuilder.setField("col1",value) ;
            binaryObjectBuilder.setField("col2",value) ;
            binaryObjectBuilder.setField("col3",value) ;
            binaryObjectBuilder.setField("col4",value) ;
            binaryObjectBuilder.setField("col5",value) ;
            mutableEntry.setValue(binaryObjectBuilder.build());
            bo = true ;

        } catch (BinaryObjectException e) {
            e.printStackTrace();
        }
        log.info("---------------TempEPBinary 2 --------"+bo);
        return bo;
    }
}
