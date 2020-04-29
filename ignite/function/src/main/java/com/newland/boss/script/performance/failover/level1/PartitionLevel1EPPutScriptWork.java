package com.newland.boss.script.performance.failover.level1;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.failover.PartitionLevel1;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionLevel1EPPutScriptWork extends PerformanceScriptWork<String, PartitionLevel1> {
    private IgniteCache<String, BinaryObject> ic;

    public PartitionLevel1EPPutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionLevel1> igniteCache, IgniteDataStreamer<String, PartitionLevel1> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        ic = igniteCache.withKeepBinary();
    }


    @Override
    public long doing() {
        long l1 = System.currentTimeMillis();
        CustObjBuild<PartitionLevel1> build = new CustObjBuild<>(PartitionLevel1.class);
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + baseKey + "";
            PartitionLevel1 obj = build.build1k(randomKey + "");
            ic.invoke(randomKey,new PutEp1(),IgniteUtil.toBinary(obj)) ;
        }
        long l2 = System.currentTimeMillis();
        return l2-l1;
    }

}
