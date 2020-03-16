package com.newland.boss.script.performance.stable;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionStableScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public PartitionStableScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public void doing() {
        //1分钟 60*1000
        //1小时 60*60*1000
        //8小时 8*60*60*1000
        Long l1 = System.currentTimeMillis();
        while (System.currentTimeMillis() - l1 < 8*60*60*1000) {
            Map<String, PartitionCustObj> map = new HashMap<>();
            CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class);
            Set<String> set = new HashSet<>() ;
            for (int i = 0; i < enterParam.getCount(); i++) {
                String randomKey = random.nextInt(enterParam.getCount()) + enterParam.getCount() + "";
                if (map.size() == enterParam.getCommitSize()) {
                    System.out.println("提交：" + map.size() + "条");
                    igniteCache.putAll(map);
                    igniteCache.getAll(set);
                    map.clear();
                    set.clear();
                }
                PartitionCustObj obj = build.build4k(randomKey + "");
                map.put(obj.getId(), obj);
                set.add(randomKey);
            }
            if (map.size() > 0) {
                System.out.println("提交：" + map.size() + "条");
                igniteCache.putAll(map);
                map.clear();
            }
        }
        System.out.println("end");


    }

}
