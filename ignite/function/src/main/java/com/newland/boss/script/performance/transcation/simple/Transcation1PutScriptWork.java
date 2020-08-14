package com.newland.boss.script.performance.transcation.simple;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.rack.MacCustObj;
import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/3/10.
 */
public class Transcation1PutScriptWork extends PerformanceScriptWork<String, TranscationCache1> {
    public Transcation1PutScriptWork(EnterParam enterParam, IgniteCache<String, TranscationCache1> igniteCache, IgniteDataStreamer<String, TranscationCache1> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<TranscationCache1> build = new CustObjBuild<>(TranscationCache1.class) ;
        List<TranscationCache1> list = new ArrayList<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            TranscationCache1 obj = build.build1k(randomKey+"") ;
            list.add(obj) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (TranscationCache1 obj:list) {
            igniteCache.put(obj.getId(),obj);
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}