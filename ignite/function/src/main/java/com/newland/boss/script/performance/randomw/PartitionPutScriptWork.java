package com.newland.boss.script.performance.randomw;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionSmallCustObj;
import org.apache.ignite.IgniteCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionPutScriptWork implements Callable<Long> {
    private int eachSize ;
    private int commitSize ;
    private IgniteCache<String, PartitionSmallCustObj> igniteCache ;
    private Random random ;
    private int count ;
    public PartitionPutScriptWork(int eachSize,int count, IgniteCache<String, PartitionSmallCustObj> igniteCache) {
        this.eachSize = eachSize ;
        this.random = new Random() ;
        this.count = count ;
        this.igniteCache = igniteCache ;
        this.commitSize = 5000 ;
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
        Map<String,PartitionSmallCustObj> map = new HashMap<>() ;
        CustObjBuild<PartitionSmallCustObj> build = new CustObjBuild<>(PartitionSmallCustObj.class) ;
        for (int i = 0; i < eachSize; i++) {
            String randomKey = random.nextInt(count)+count+"" ;
            System.out.println(randomKey);
            if (map.size()==commitSize){
                igniteCache.putAll(map);
                map.clear();
                System.out.println("提交"+commitSize+"条");
            }
            PartitionSmallCustObj partitionSmallCustObj = build.build1k(randomKey+"") ;
            map.put(partitionSmallCustObj.getId(),partitionSmallCustObj) ;
        }
        System.out.println("----"+map.size());
        if (map.size()>0){
            igniteCache.putAll(map);
            map.clear();
        }
    }
}
