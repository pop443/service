package com.newland.boss.script.performance.randomw.partitionmanyput.lock;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj2;
import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache2;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

/**
 * Created by xz on 2020/3/10.
 */
public class LockEPmanyPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,PartitionCustObj> igniteCache1 ;
    private IgniteCache<String,PartitionCustObj2> igniteCache2 ;
    private IgniteCache<String,BinaryObject> ic1 ;
    private IgniteCache<String,BinaryObject> ic2 ;
    private Ignite ignite ;
    private Integer baseKey;
    public LockEPmanyPutScriptWork(EnterParam enterParam, Ignite ignite, IgniteCache<String,PartitionCustObj> igniteCache1, IgniteCache<String,PartitionCustObj2> igniteCache2, Integer baseKey) {
        this.enterParam = enterParam;
        this.igniteCache1 = igniteCache1 ;
        this.igniteCache2 = igniteCache2 ;
        this.ic1 = igniteCache1.withKeepBinary() ;
        this.ic2 = igniteCache2.withKeepBinary() ;
        this.baseKey = baseKey ;
        this.ignite = ignite ;
    }

    @Override
    public Long call() throws Exception {
        return working();
    }

    private Long working() {
        List<BinaryObject> list1 = new ArrayList<>() ;
        List<BinaryObject> list2 = new ArrayList<>() ;
        CustObjBuild<PartitionCustObj> build1 = new CustObjBuild<>(PartitionCustObj.class) ;
        CustObjBuild<PartitionCustObj2> build2 = new CustObjBuild<>(PartitionCustObj2.class) ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey1 = i+baseKey+"" ;
            String randomKey2 = i+baseKey+"" ;
            PartitionCustObj bigObj1 = build1.build1k(randomKey1) ;
            PartitionCustObj2 smallObj1 = build2.build1k(randomKey2) ;
            list1.add(IgniteUtil.toBinary(bigObj1)) ;
            list2.add(IgniteUtil.toBinary(smallObj1)) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;

        for (int i = 0; i < list1.size(); i++) {
            Lock lock1 = igniteCache1.lock(list1.get(i).field("id")) ;
            Lock lock2 = igniteCache2.lock(list2.get(i).field("id")) ;
            try {
                lock1.lock();
                lock2.lock();
                ic1.invoke(list1.get(i).field("id"),new PutEp1(),list1.get(i)) ;
                ic2.invoke(list2.get(i).field("id"),new PutEp1(),list2.get(i)) ;

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }
}
