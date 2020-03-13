package com.newland.boss.script.performance.randomw.partitionsmallput;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionSmallCustObj;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.IgniteCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String, PartitionSmallCustObj> igniteCache ;
    private Random random ;
    public PartitionSmallPutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionSmallCustObj> igniteCache) {
        this.random = new Random() ;
        this.enterParam = enterParam ;
        this.igniteCache = igniteCache ;
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
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = random.nextInt(enterParam.getCount())+enterParam.getCount()+"" ;
            if (map.size()==enterParam.getCommitSize()){
                System.out.println("提交："+map.size()+"条");
                igniteCache.putAll(map);
                map.clear();
            }
            PartitionSmallCustObj obj = build.build1k(randomKey+"") ;
            map.put(obj.getId(),obj) ;
        }
        if (map.size()>0){
            System.out.println("提交："+map.size()+"条");
            igniteCache.putAll(map);
            map.clear();
        }
    }
}
