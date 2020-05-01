package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.transactions.TransactionException;

/**
 * Created by xz on 2020/4/30.
 */
public class EPTestWork extends PerformanceScriptWork<String, Expiry> {
    private IgniteCache<String, BinaryObject> ic;

    public EPTestWork(EnterParam enterParam, IgniteCache<String, Expiry> igniteCache, IgniteDataStreamer<String, Expiry> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer, baseKey);
        ic = igniteCache.withKeepBinary();
    }


    @Override
    public long doing() {
        try {
            System.out.println("休眠");
            Thread.sleep(5000L);
            for (int j = 10; j < 13; j++) {

                String randomKey = j + "";
                Expiry obj1 = new Expiry();
                obj1.setId(randomKey);
                obj1.setAutomation(new Automation(randomKey, 1, randomKey));
                System.out.println(obj1);
                ic.invoke(obj1.getId(), new PutEp1(), IgniteUtil.toBinary(obj1));
            }
            System.out.println("休眠");
            Thread.sleep(10000L);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
