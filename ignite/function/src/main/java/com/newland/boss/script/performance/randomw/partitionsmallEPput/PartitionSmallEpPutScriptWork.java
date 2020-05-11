package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;

import javax.cache.processor.EntryProcessorResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallEpPutScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    private IgniteCache<String,BinaryObject> ic ;
    public PartitionSmallEpPutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        ic = igniteCache.withKeepBinary() ;
    }


    @Override
    public long doing() {
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class);
        List<PartitionCustObj> list = new ArrayList<>() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + baseKey + "";
            PartitionCustObj obj1 = build.build1k(randomKey + "-1");
            PartitionCustObj obj2 = build.build1k(randomKey + "-2");
            PartitionCustObj obj3 = build.build1k(randomKey + "-3");
            PartitionCustObj obj4 = build.build1k(randomKey + "-4");
            list.add(obj1);
            list.add(obj2);
            list.add(obj3);
            list.add(obj4);
        }
        long l1 = System.currentTimeMillis() ;
        for (PartitionCustObj obj:list) {
            ic.invoke(obj.getId(),new PutEp1(),IgniteUtil.toBinary(obj)) ;
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }

}
