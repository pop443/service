package com.newland.boss.script.performance.complex;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.complex.ComplexKey;
import com.newland.boss.entity.performance.complex.ComplexValue;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
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
public class ComplexStreamPutScriptWork extends PerformanceScriptWork<ComplexKey,ComplexValue> {
    public ComplexStreamPutScriptWork(EnterParam enterParam, IgniteCache<ComplexKey,ComplexValue> igniteCache, IgniteDataStreamer<ComplexKey,ComplexValue> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public void doing() {
        Map<ComplexKey,ComplexValue> map = new HashMap<>() ;
        CustObjBuild<ComplexValue> build = new CustObjBuild<>(ComplexValue.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            if (map.size() == enterParam.getCommitSize()) {
                System.out.println("提交：" + map.size() + "条");
                ids.addData(map);
                ids.flush();
                map.clear();
            }
            ComplexValue obj = build.build1k(randomKey+"") ;
            Long userId = BeanUtil.user_id() ;
            ComplexKey complexKey = new ComplexKey(randomKey, userId,userId,userId+"",userId+"") ;
            map.put(complexKey,obj) ;
        }
        if (map.size() > 0) {
            System.out.println("提交：" + map.size() + "条");
            ids.addData(map);
            ids.flush();
            map.clear();
        }
    }
}
