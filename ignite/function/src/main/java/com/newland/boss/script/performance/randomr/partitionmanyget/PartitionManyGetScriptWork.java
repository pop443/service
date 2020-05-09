package com.newland.boss.script.performance.randomr.partitionmanyget;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj2;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.IgniteCache;

import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionManyGetScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,PartitionCustObj> igniteCache1 ;
    private IgniteCache<String,PartitionCustObj2> igniteCache2 ;
    private Integer baseKey ;
    public PartitionManyGetScriptWork(EnterParam enterParam, IgniteCache<String,PartitionCustObj> igniteCache1, IgniteCache<String,PartitionCustObj2> igniteCache2,Integer baseKey) {
        this.enterParam = enterParam ;
        this.igniteCache1 = igniteCache1 ;
        this.igniteCache2 = igniteCache2 ;
        this.baseKey = baseKey ;
    }

    @Override
    public Long call() throws Exception {
        return working();
    }

    private long working() {
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            igniteCache1.get(randomKey+"-1");
            igniteCache1.get(randomKey+"-2");
            igniteCache2.get(randomKey+"-1");
            igniteCache2.get(randomKey+"-2");
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }

}
