package com.newland.boss.script.performance.randomw.partitionmanyput;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj2;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.transactions.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionManyPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,PartitionCustObj> igniteCache1 ;
    private IgniteCache<String,PartitionCustObj2> igniteCache2 ;
    private Integer baseKey;
    public PartitionManyPutScriptWork(EnterParam enterParam, IgniteCache<String,PartitionCustObj> igniteCache1, IgniteCache<String,PartitionCustObj2> igniteCache2,Integer baseKey) {
        this.enterParam = enterParam;
        this.igniteCache1 = igniteCache1 ;
        this.igniteCache2 = igniteCache2 ;
        this.baseKey = baseKey ;
    }

    @Override
    public Long call() throws Exception {
        return working();
    }

    private Long working() {
        System.out.println(" start ");
        long l1 = System.currentTimeMillis() ;
        CustObjBuild<PartitionCustObj> build1 = new CustObjBuild<>(PartitionCustObj.class) ;
        CustObjBuild<PartitionCustObj2> build2 = new CustObjBuild<>(PartitionCustObj2.class) ;
        Map<String,PartitionCustObj> map1 = new HashMap<>() ;
        Map<String,PartitionCustObj2> map2 = new HashMap<>() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey1 = i+baseKey+"" ;
            String randomKey2 = i+baseKey+"" ;

            PartitionCustObj bigObj1 = build1.build1k(randomKey1+"-1") ;
            PartitionCustObj bigObj2 = build1.build1k(randomKey1+"-2") ;
            PartitionCustObj2 smallObj1 = build2.build1k(randomKey2+"-1") ;
            PartitionCustObj2 smallObj2 = build2.build1k(randomKey2+"-2") ;
            map1.put(bigObj1.getId(),bigObj1) ;
            map1.put(bigObj2.getId(),bigObj2) ;
            map2.put(smallObj1.getId(),smallObj1) ;
            map2.put(smallObj2.getId(),smallObj2) ;
            igniteCache1.putAll(map1);
            igniteCache2.putAll(map2) ;
            map1.clear();
            map2.clear();
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }
}
