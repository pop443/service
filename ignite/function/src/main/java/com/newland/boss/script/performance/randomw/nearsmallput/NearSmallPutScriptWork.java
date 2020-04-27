package com.newland.boss.script.performance.randomw.nearsmallput;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.NearSmallCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class NearSmallPutScriptWork extends PerformanceScriptWork<String, NearSmallCustObj> {
    public NearSmallPutScriptWork(EnterParam enterParam, IgniteCache<String, NearSmallCustObj> igniteCache, IgniteDataStreamer<String, NearSmallCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public long doing() {
        long cost = 0 ;
        Map<String,NearSmallCustObj> map = new HashMap<>() ;
        CustObjBuild<NearSmallCustObj> build = new CustObjBuild<>(NearSmallCustObj.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+enterParam.getCount()+"" ;
            NearSmallCustObj obj = build.build1k(randomKey+"") ;
            map.put(obj.getId(),obj) ;
        }
        if (map.size()>0){
            long l1 = System.currentTimeMillis() ;
            igniteCache.putAll(map);
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
            map.clear();
        }
        return cost ;
    }

}
