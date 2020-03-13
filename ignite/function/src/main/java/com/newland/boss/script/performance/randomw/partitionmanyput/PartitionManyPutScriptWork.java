package com.newland.boss.script.performance.randomw.partitionmanyput;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionBigCustObj;
import com.newland.boss.entity.performance.obj.PartitionSmallCustObj;
import org.apache.ignite.IgniteCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionManyPutScriptWork implements Callable<Long> {
    private int eachSize ;
    private int commitSize ;
    private IgniteCache<String,PartitionBigCustObj> bigIgniteCache ;
    private IgniteCache<String,PartitionSmallCustObj> smallIgniteCache ;
    private Random random ;
    private int count ;
    public PartitionManyPutScriptWork(int eachSize, int count, IgniteCache<String,PartitionBigCustObj> bigIgniteCache, IgniteCache<String,PartitionSmallCustObj> smallIgniteCache) {
        this.eachSize = eachSize ;
        this.random = new Random() ;
        this.count = count ;
        this.bigIgniteCache = bigIgniteCache ;
        this.smallIgniteCache = smallIgniteCache ;
        this.commitSize = Constant.batchSize ;
    }

    @Override
    public Long call() throws Exception {
        Long l1 = System.currentTimeMillis() ;
        try {
            working();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }

    private void working() {
        Map<String,PartitionBigCustObj> bigMap = new HashMap<>() ;
        Map<String,PartitionSmallCustObj> smallMap = new HashMap<>() ;
        CustObjBuild<PartitionBigCustObj> bigBuild = new CustObjBuild<>(PartitionBigCustObj.class) ;
        CustObjBuild<PartitionSmallCustObj> smallBuild = new CustObjBuild<>(PartitionSmallCustObj.class) ;
        for (int i = 0; i < eachSize; i++) {
            String randomKey = random.nextInt(count)+count+"" ;
            if (bigMap.size()==commitSize){
                bigIgniteCache.putAll(bigMap);
                smallIgniteCache.putAll(smallMap);
                bigMap.clear();
                smallMap.clear();
                System.out.println("提交"+commitSize+"条");
            }
            PartitionBigCustObj bigObj = bigBuild.build1k(randomKey+"") ;
            PartitionSmallCustObj smallObj = smallBuild.build1k(randomKey+"") ;
            bigMap.put(bigObj.getId(),bigObj) ;
            smallMap.put(smallObj.getId(),smallObj) ;
        }
        System.out.println("----"+bigMap.size());
        if (bigMap.size()>0){
            bigIgniteCache.putAll(bigMap);
            smallIgniteCache.putAll(smallMap);
            bigMap.clear();
            smallMap.clear();
        }
    }
}
