package com.newland.boss.script.performance.failover.level2;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.failover.PartitionLevel2;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionLevel2PutScriptWork extends PerformanceScriptWork<String, PartitionLevel2> {
    public PartitionLevel2PutScriptWork(EnterParam enterParam, IgniteCache<String, PartitionLevel2> igniteCache, IgniteDataStreamer<String, PartitionLevel2> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public void doing() {
        Map<String,PartitionLevel2> map = new HashMap<>() ;
        CustObjBuild<PartitionLevel2> build = new CustObjBuild<>(PartitionLevel2.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            if (map.size() == enterParam.getCommitSize()) {
                System.out.println("提交：" + map.size() + "条");
                ids.addData(map);
                ids.flush();
                map.clear();
            }
            PartitionLevel2 obj = build.build1k(randomKey+"") ;
            map.put(obj.getId(),obj) ;
        }
        if (map.size() > 0) {
            System.out.println("提交：" + map.size() + "条");
            ids.addData(map);
            ids.flush();
            map.clear();
        }
    }

}
