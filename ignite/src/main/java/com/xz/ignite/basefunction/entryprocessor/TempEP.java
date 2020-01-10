package com.xz.ignite.basefunction.entryprocessor;

import com.xz.ignite.basefunction.entity.Temp;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.resources.LoggerResource;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.util.UUID;

/**
 * Created by Administrator on 2020/1/3.
 */
public class TempEP implements CacheEntryProcessor<UUID,Temp,Boolean>{
    @LoggerResource
    private IgniteLogger log;
    @Override
    public Boolean process(MutableEntry<UUID, Temp> mutableEntry, Object... objects) throws EntryProcessorException {
        Boolean bo = false ;
        try {
            String value = (String)objects[0] ;
            Temp temp = mutableEntry.getValue() ;
            temp.setCol1(value);
            temp.setCol2(value);
            temp.setCol3(value);
            temp.setCol4(value);
            temp.setCol5(value);
            mutableEntry.setValue(temp);
            bo = true ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("---------------TempEP 11--------"+bo);
        return bo;
    }
}
