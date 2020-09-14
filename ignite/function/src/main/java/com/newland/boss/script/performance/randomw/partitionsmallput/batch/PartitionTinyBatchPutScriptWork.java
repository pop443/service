package com.newland.boss.script.performance.randomw.partitionsmallput.batch;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.transcation.TranscationCache1;
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
public class PartitionTinyBatchPutScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionTinyBatchPutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class) ;
        List<PartitionCustObj> list = new ArrayList<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            PartitionCustObj obj = build.build1k(randomKey+"") ;
            list.add(obj) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        Map<String,PartitionCustObj> map = new HashMap<>() ;
        for (int i = 0; i < list.size(); i++) {
            if (map.size()==enterParam.getBatchSize()){
                commit(map);
            }
            PartitionCustObj cache1 = list.get(i) ;
            map.put(cache1.getId(),cache1) ;
        }
        if (map.size()>0){
            commit(map);
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
    private void commit(Map<String, PartitionCustObj> map) {
        igniteCache.putAll(map);
        map.clear();
    }
}