package com.newland.boss.script.performance.stream;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.NearSmallCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class DemoScriptWork implements Callable<Long>{
    private EnterParam enterParam ;
    private Integer baseKey ;
    public DemoScriptWork(EnterParam enterParam, Integer baseKey) {
        this.enterParam = enterParam ;
        this.baseKey = baseKey ;
    }

    @Override
    public Long call() throws Exception {
        return doing();
    }

    public long doing() {
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            System.out.println(randomKey);
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }

}
