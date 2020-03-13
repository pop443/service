package com.newland.boss.script.performance.randomr.nearsmallget;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.NearSmallCustObj;
import org.apache.ignite.IgniteCache;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class NearSmallGetScriptWork implements Callable<Long> {
    private int eachSize ;
    private int commitSize ;
    private IgniteCache<String, NearSmallCustObj> igniteCache ;
    private Random random ;
    private int count ;
    public NearSmallGetScriptWork(int eachSize, int count, IgniteCache<String, NearSmallCustObj> igniteCache) {
        this.eachSize = eachSize ;
        this.random = new Random() ;
        this.count = count ;
        this.igniteCache = igniteCache ;
        this.commitSize = Constant.batchSize;
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
        Set<String> set = new HashSet<>(commitSize) ;
        for (int i = 0; i < eachSize; i++) {
            String randomKey = random.nextInt(count)+count+"" ;
            set.add(randomKey);
            if (set.size()==commitSize){
                int getCount = igniteCache.getAll(set).size();
                System.out.println("读取"+commitSize+"条:实际获取"+getCount+"条");
                set.clear();
            }
        }
        if (set.size()>0){
            int getCount = igniteCache.getAll(set).size();
            System.out.println("读取"+set.size()+"条:实际获取"+getCount+"条");
            set.clear();
        }
    }
}
