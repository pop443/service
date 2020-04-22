package com.newland.boss.script.performance.randomr.nearsmallget;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.NearSmallCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class NearSmallGetScriptWork extends PerformanceScriptWork<String, NearSmallCustObj> {
    public NearSmallGetScriptWork(EnterParam enterParam, IgniteCache<String, NearSmallCustObj> igniteCache, IgniteDataStreamer<String, NearSmallCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public void doing() {
        Set<String> set = new HashSet<>(enterParam.getCommitSize()) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+enterParam.getCount()+"" ;
            set.add(randomKey);
            if (set.size()==enterParam.getCommitSize()){
                int getCount = igniteCache.getAll(set).size();
                System.out.println(Thread.currentThread().getName()+"读取"+enterParam.getCommitSize()+"条:实际获取"+getCount+"条");
                set.clear();
            }
        }
        if (set.size()>0){
            int getCount = igniteCache.getAll(set).size();
            System.out.println(Thread.currentThread().getName()+"读取"+enterParam.getCommitSize()+"条:实际获取"+getCount+"条");
            set.clear();
        }
    }

}
