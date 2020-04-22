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
    public void doing() {
        Map<String,SimpleValue> map = new HashMap<>() ;
        CustObjBuild<SimpleValue> build = new CustObjBuild<>(SimpleValue.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            if (map.size() == enterParam.getCommitSize()) {
                System.out.println("提交：" + map.size() + "条");
                ids.addData(map);
                ids.flush();
                map.clear();
            }
            SimpleValue obj = build.build1k(randomKey+"") ;
            Long userId = BeanUtil.user_id() ;
            String key = randomKey+"--"+userId+"--"+userId+"--"+userId+"--"+userId ;
            map.put(key,obj) ;
        }
        if (map.size() > 0) {
            System.out.println("提交：" + map.size() + "条");
            ids.addData(map);
            ids.flush();
            map.clear();
        }
    }
}
