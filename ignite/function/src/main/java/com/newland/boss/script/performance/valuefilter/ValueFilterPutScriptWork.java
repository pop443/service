package com.newland.boss.script.performance.valuefilter;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.affinity.AffinityItemYes;
import com.newland.boss.entity.performance.affinity.AffinityItemYesKey;
import com.newland.boss.entity.performance.complex.ListObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class ValueFilterPutScriptWork extends PerformanceScriptWork<String,ListObj> {
    public ValueFilterPutScriptWork(EnterParam enterParam, IgniteCache<String,ListObj> igniteCache, IgniteDataStreamer<String,ListObj> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<ListObj> build = new CustObjBuild<>(ListObj.class) ;
        List<ListObj> list = new ArrayList<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            ListObj obj = build.build1k(randomKey);
            list.add(obj) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (ListObj obj:list) {
            igniteCache.put(obj.getId(),obj);
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}