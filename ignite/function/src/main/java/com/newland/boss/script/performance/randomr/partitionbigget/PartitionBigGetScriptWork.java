package com.newland.boss.script.performance.randomr.partitionbigget;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionBigCustObj;
import org.apache.ignite.IgniteCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionBigGetScriptWork implements Callable<Long> {
    private int eachSize ;
    private int commitSize ;
    private IgniteCache<String, PartitionBigCustObj> igniteCache ;
    private Random random ;
    private int count ;
    public PartitionBigGetScriptWork(int eachSize, int count, IgniteCache<String, PartitionBigCustObj> igniteCache) {
        this.eachSize = eachSize ;
        this.random = new Random() ;
        this.count = count ;
        this.igniteCache = igniteCache ;
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
        Map<String,PartitionBigCustObj> map = new HashMap<>() ;
        CustObjBuild<PartitionBigCustObj> build = new CustObjBuild<>(PartitionBigCustObj.class) ;
        for (int i = 0; i < eachSize; i++) {
            String randomKey = random.nextInt(count)+count+"" ;
            if (map.size()==commitSize){
                igniteCache.putAll(map);
                map.clear();
                System.out.println("提交"+commitSize+"条");
            }
            PartitionBigCustObj obj = build.build4k(randomKey+"") ;
            map.put(obj.getId(),obj) ;
        }
        System.out.println("----"+map.size());
        if (map.size()>0){
            igniteCache.putAll(map);
            map.clear();
        }
    }
}
