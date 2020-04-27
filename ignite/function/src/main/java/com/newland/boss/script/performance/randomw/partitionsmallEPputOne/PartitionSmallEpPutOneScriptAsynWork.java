package com.newland.boss.script.performance.randomw.partitionsmallEPputOne;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheEntryProcessor;
import org.apache.ignite.lang.IgniteFuture;

import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import javax.cache.processor.MutableEntry;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallEpPutOneScriptAsynWork extends PerformanceScriptWork<String, PartitionCustObj> {
    private IgniteCache<String, BinaryObject> ic;

    public PartitionSmallEpPutOneScriptAsynWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
        ic = igniteCache.withKeepBinary();
    }


    @Override
    public long doing() {
        long cost = 0 ;
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class);
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            PartitionCustObj obj = build.build1k(randomKey + "");
            BinaryObject binaryObject = IgniteUtil.toBinary(obj);
            long l1 = System.currentTimeMillis() ;
            ic.invoke(randomKey, new CacheEntryProcessor<String, BinaryObject, Object>() {
                @Override
                public Object process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
                    BinaryObject binaryObject1 = (BinaryObject)objects[0] ;
                    mutableEntry.setValue(binaryObject1) ;
                    return null;
                }
            }, binaryObject);
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
        }
        return cost ;
    }

}
