package com.newland.boss.script.performance.randomw.partitionsmallputsamekey;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallPutSameKeyScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionSmallPutSameKeyScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class) ;
        List<PartitionCustObj> list = new ArrayList<>() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            PartitionCustObj obj = build.build1k("1") ;
            list.add(obj);
        }
        long l1 = System.currentTimeMillis() ;
        for (PartitionCustObj obj:list) {
            igniteCache.put(obj.getId(),obj);
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}