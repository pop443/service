package com.newland.boss.script.performance.rack.rackaware;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.rack.Rack1CustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/3/10.
 */
public class Rack1PutScriptWork extends PerformanceScriptWork<String, Rack1CustObj> {
    public Rack1PutScriptWork(EnterParam enterParam, IgniteCache<String, Rack1CustObj> igniteCache, IgniteDataStreamer<String, Rack1CustObj> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<Rack1CustObj> build = new CustObjBuild<>(Rack1CustObj.class) ;
        List<Rack1CustObj> list = new ArrayList<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            Rack1CustObj obj = build.build1k(randomKey+"") ;
            list.add(obj) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (Rack1CustObj obj:list) {
            igniteCache.put(obj.getId(),obj);
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}