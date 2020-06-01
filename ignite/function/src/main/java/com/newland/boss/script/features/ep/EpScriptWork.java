package com.newland.boss.script.features.ep;

import com.newland.boss.entity.ep.EpDemo;
import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj2;
import com.newland.boss.entity.performance.rebalance.Rebalance1;
import com.newland.boss.script.performance.EnterParam;
import com.newland.ignite.entryprocessor.AddEp;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.lang.IgniteFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2020/3/10.
 */
public class EpScriptWork implements Callable<Long> {
    private int index;
    private IgniteCache<String, BinaryObject> ic ;

    public EpScriptWork(int index, IgniteCache<String, BinaryObject> ic) {
        this.index = index;
        this.ic = ic;
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
        String key = "1" ;
        ic.invoke(key, new AddEp(),index);
        IgniteFuture igniteFuture = ic.invokeAsync(key, new AddEp(),index);
        igniteFuture.get(10, TimeUnit.SECONDS);
    }
}
