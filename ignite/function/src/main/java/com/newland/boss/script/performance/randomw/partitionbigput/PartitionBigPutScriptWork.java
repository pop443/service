package com.newland.boss.script.performance.randomw.partitionbigput;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionBigPutScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionBigPutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class) ;
        List<PartitionCustObj> list =  new ArrayList<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            PartitionCustObj obj = build.build10k(randomKey+"") ;
            list.add(obj) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (PartitionCustObj obj:list) {
            igniteCache.put(obj.getId(),obj);
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}
