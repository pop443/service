package com.newland.boss.script.performance.rack.macaware;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.rack.MacCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/3/10.
 */
public class MacPutScriptWork extends PerformanceScriptWork<String, MacCustObj> {
    public MacPutScriptWork(EnterParam enterParam, IgniteCache<String, MacCustObj> igniteCache, IgniteDataStreamer<String, MacCustObj> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<MacCustObj> build = new CustObjBuild<>(MacCustObj.class) ;
        List<MacCustObj> list = new ArrayList<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            MacCustObj obj = build.build1k(randomKey+"") ;
            list.add(obj) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (MacCustObj obj:list) {
            igniteCache.put(obj.getId(),obj);
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}