package com.newland.boss.script.performance.randomw.partitionsmallstreamput;

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
public class PartitionSmallSteamPutScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionSmallSteamPutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public long doing() {
        long cost = 0 ;
        Map<String,PartitionCustObj> map = new HashMap<>() ;
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            PartitionCustObj obj = build.build1k(randomKey+"") ;
            map.put(obj.getId(),obj) ;
        }
        if (map.size() > 0) {
            long l1 = System.currentTimeMillis() ;
            ids.addData(map);
            ids.flush();
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
            map.clear();
        }
        return cost ;
    }

}
