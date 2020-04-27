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
import java.util.HashMap;
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
        long cost = 0 ;
        Map<String, BinaryObject> map = new HashMap<>();
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class);
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + baseKey + "";
            PartitionCustObj obj = build.build1k(randomKey + "");
            map.put(obj.getId(), IgniteUtil.toBinary(obj));
        }
        if (map.size() > 0) {
            long l1 = System.currentTimeMillis() ;
            Map<String, EntryProcessorResult<Boolean>> resultMap = ic.invokeAll(map.keySet(), new PutEp1(), map);
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
            map.clear();
        }
        return cost ;
    }

}
