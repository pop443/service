package com.newland.boss.script.performance.stream;

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
public class PartitionPutScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionPutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        long l1 = System.currentTimeMillis() ;
        Map<String,PartitionCustObj> map = new HashMap<>() ;
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            if (map.size()==enterParam.getBatchSize()){
                igniteCache.putAll(map);
                map.clear();
            }
            String randomKey = i+baseKey+"" ;
            PartitionCustObj obj = build.build1k(randomKey+"") ;
            map.put(obj.getId(),obj) ;
        }
        if (map.size()>0){
            igniteCache.putAll(map);
            map.clear();
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}