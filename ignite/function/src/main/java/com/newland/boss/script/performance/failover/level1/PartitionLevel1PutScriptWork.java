package com.newland.boss.script.performance.failover.level1;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.failover.PartitionLevel1;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionLevel1PutScriptWork extends PerformanceScriptWork<String, PartitionLevel1> {
    public PartitionLevel1PutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionLevel1> igniteCache, IgniteDataStreamer<String, PartitionLevel1> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<PartitionLevel1> build = new CustObjBuild<>(PartitionLevel1.class) ;
        List<PartitionLevel1> list = new ArrayList<>() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + baseKey + "";
            PartitionLevel1 obj = build.build1k(randomKey+"") ;
            list.add(obj) ;
        }
        long l1 = System.currentTimeMillis() ;
        for (PartitionLevel1 obj:list) {
            igniteCache.put(obj.getId(),obj);
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }

}
