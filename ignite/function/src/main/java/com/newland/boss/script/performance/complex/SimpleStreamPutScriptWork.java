package com.newland.boss.script.performance.complex;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.complex.SimpleValue;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.ignite.label.utils.BeanUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class SimpleStreamPutScriptWork extends PerformanceScriptWork<String,SimpleValue> {
    public SimpleStreamPutScriptWork(EnterParam enterParam, IgniteCache<String,SimpleValue> igniteCache, IgniteDataStreamer<String,SimpleValue> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public long doing() {
        long cost = 0 ;
        Map<String,SimpleValue> map = new HashMap<>() ;
        CustObjBuild<SimpleValue> build = new CustObjBuild<>(SimpleValue.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            SimpleValue obj = build.build1k(randomKey+"") ;
            Long userId = BeanUtil.user_id() ;
            String key = randomKey+"--"+userId+"--"+userId+"--"+userId+"--"+userId ;
            map.put(key,obj) ;
        }
        if (map.size() > 0) {
            long l1 = System.currentTimeMillis() ;
            ids.addData(map);
            ids.flush();
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
            map.clear();
        }
        return cost ;
    }
}
