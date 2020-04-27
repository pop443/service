package com.newland.boss.script.performance.randomw.partitionsmallputsamekey;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallPutSameKeyScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionSmallPutSameKeyScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public long doing() {
        long cost = 0 ;
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class) ;
        PartitionCustObj obj = build.build1k("1") ;
        long l1 = System.currentTimeMillis() ;
        igniteCache.put(obj.getId(),obj);
        long l2 = System.currentTimeMillis() ;
        cost = cost+(l2-l1);
        return cost ;
    }
}