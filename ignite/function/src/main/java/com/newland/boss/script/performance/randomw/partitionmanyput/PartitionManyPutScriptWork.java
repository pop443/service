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
    private Ignite ignite ;
    private Random random;
    public PartitionManyPutScriptWork(EnterParam enterParam, IgniteCache<String,PartitionCustObj> igniteCache1, IgniteCache<String,PartitionCustObj2> igniteCache2,Ignite ignite) {
        this.random = new Random();
        this.enterParam = enterParam;
        this.igniteCache1 = igniteCache1 ;
        this.igniteCache2 = igniteCache2 ;
        this.ignite = ignite ;
    }

    @Override
    public Long call() throws Exception {

        return working();
    }

    private Long working() {
        long cost = 0 ;
        Map<String,PartitionCustObj> map1 = new HashMap<>(enterParam.getCount()) ;
        Map<String,PartitionCustObj2> map2 = new HashMap<>(enterParam.getCount()) ;
        CustObjBuild<PartitionCustObj> build1 = new CustObjBuild<>(PartitionCustObj.class) ;
        CustObjBuild<PartitionCustObj2> build2 = new CustObjBuild<>(PartitionCustObj2.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            if (map1.size()==enterParam.getCommitSize()){
                long l1 = System.currentTimeMillis() ;
                igniteCache1.putAll(map1);
                igniteCache2.putAll(map2);
                long l2 = System.currentTimeMillis() ;
                cost = cost+(l2-l1);
                map1.clear();
                map2.clear();
            }
            String randomKey1 = i+enterParam.getCount()+"" ;
            String randomKey2 = i+enterParam.getCount()+"" ;
            PartitionCustObj bigObj = build1.build1k(randomKey1+"") ;
            PartitionCustObj2 smallObj = build2.build1k(randomKey2+"") ;
            map1.put(bigObj.getId(),bigObj) ;
            map2.put(smallObj.getId(),smallObj) ;
        }

        if (map1.size()>0){
            long l1 = System.currentTimeMillis() ;
            igniteCache1.putAll(map1);
            igniteCache2.putAll(map2);
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
            map1.clear();
            map2.clear();
        }
        return cost;
    }
}
