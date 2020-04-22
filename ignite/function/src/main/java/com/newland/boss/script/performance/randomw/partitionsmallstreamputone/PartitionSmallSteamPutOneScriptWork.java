package com.newland.boss.script.performance.randomw.partitionsmallstreamputone;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallSteamPutOneScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionSmallSteamPutOneScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public void doing() {
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            PartitionCustObj obj = build.build1k(randomKey+"") ;
            ids.addData(randomKey,obj);
            ids.flush();
        }
    }

}
