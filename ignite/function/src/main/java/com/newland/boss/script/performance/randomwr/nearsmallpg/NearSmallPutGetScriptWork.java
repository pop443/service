package com.newland.boss.script.performance.randomwr.nearsmallpg;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.NearSmallCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/3/10.
 */
public class NearSmallPutGetScriptWork extends PerformanceScriptWork<String, NearSmallCustObj> {
    public NearSmallPutGetScriptWork(EnterParam enterParam, IgniteCache<String, NearSmallCustObj> igniteCache, IgniteDataStreamer<String, NearSmallCustObj> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {

        CustObjBuild<NearSmallCustObj> build = new CustObjBuild<>(NearSmallCustObj.class) ;
        List<NearSmallCustObj> list = new ArrayList<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            NearSmallCustObj obj = build.build4k(randomKey+"") ;
            list.add(obj) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (NearSmallCustObj obj:list) {
            igniteCache.put(obj.getId(),obj);
            igniteCache.get(obj.getId());
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }

}
