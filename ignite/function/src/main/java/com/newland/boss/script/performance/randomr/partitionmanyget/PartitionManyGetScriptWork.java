package com.newland.boss.script.performance.randomr.partitionmanyget;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj2;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.lang.IgniteFuture;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionManyGetScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,PartitionCustObj> igniteCache1 ;
    private IgniteCache<String,PartitionCustObj2> igniteCache2 ;
    private Random random ;
    public PartitionManyGetScriptWork(EnterParam enterParam, IgniteCache<String,PartitionCustObj> igniteCache1, IgniteCache<String,PartitionCustObj2> igniteCache2) {
        this.enterParam = enterParam ;
        this.random = new Random() ;
        this.igniteCache1 = igniteCache1 ;
        this.igniteCache2 = igniteCache2 ;
    }

    @Override
    public Long call() throws Exception {
        return working();
    }

    private long working() {
        long cost = 0 ;
        Set<String> set1 = new HashSet<>(enterParam.getCount());
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+enterParam.getCount()+"" ;
            set1.add(randomKey);
        }
        if (set1.size()>0){
            long l1 = System.currentTimeMillis() ;
            IgniteFuture<Map<String,PartitionCustObj>> igniteFuture1 = igniteCache1.getAllAsync(set1) ;
            IgniteFuture<Map<String,PartitionCustObj2>> igniteFuture2 = igniteCache2.getAllAsync(set1) ;
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
            set1.clear();
        }
        return cost ;
    }

}
