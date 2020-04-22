package com.newland.boss.script.performance.randomw.partitionbigstreamput;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionBigSteamPutScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionBigSteamPutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public void doing() {
        Map<String, PartitionCustObj> map = new HashMap<>();
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class);
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            if (map.size() == enterParam.getCommitSize()) {
                System.out.println("提交：" + map.size() + "条");
                ids.addData(map);
                ids.flush();
                map.clear();
            }
            PartitionCustObj obj = build.build4k(randomKey + "");
            map.put(obj.getId(), obj);
        }
        if (map.size() > 0) {
            System.out.println("提交：" + map.size() + "条");
            ids.addData(map);
            ids.flush();
            map.clear();
        }
    }

}
