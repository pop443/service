package com.newland.boss.script.performance.randomw.partitionmanyput;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj2;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.transactions.Transaction;

import java.util.*;
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
        List<PartitionCustObj> list1 = new ArrayList<>() ;
        List<PartitionCustObj2> list2 = new ArrayList<>() ;
        CustObjBuild<PartitionCustObj> build1 = new CustObjBuild<>(PartitionCustObj.class) ;
        CustObjBuild<PartitionCustObj2> build2 = new CustObjBuild<>(PartitionCustObj2.class) ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey1 = i+baseKey+"" ;
            String randomKey2 = i+baseKey+"" ;
            PartitionCustObj bigObj1 = build1.build1k(randomKey1) ;
            PartitionCustObj2 smallObj1 = build2.build1k(randomKey2) ;
            list1.add(bigObj1) ;
            list2.add(smallObj1) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < list1.size(); i++) {
            igniteCache1.put(list1.get(i).getId()+"-1",list1.get(i)) ;
            igniteCache1.put(list1.get(i).getId()+"-2",list1.get(i)) ;
            igniteCache2.put(list2.get(i).getId()+"-1",list2.get(i)) ;
            igniteCache2.put(list2.get(i).getId()+"-2",list2.get(i)) ;
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }
}
