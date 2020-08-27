package com.newland.boss.script.performance.connection;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.ignite.label.utils.IdGen;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by xz on 2020/3/10.
 */
public class ConnectionNumScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public ConnectionNumScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class) ;
        long l1 = System.currentTimeMillis() ;
        int count = 1 ;
        try {
            while (count==1){
                PartitionCustObj obj = build.build1k(IdGen.uuid()) ;
                igniteCache.put(obj.getId(),obj);
                Thread.sleep(10000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}