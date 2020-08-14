package com.newland.boss.script.performance.transcation.simple;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;

import java.util.*;

/**
 * Created by xz on 2020/3/10.
 */
public class Transcation1EpPutScriptWork extends PerformanceScriptWork<String, TranscationCache1> {
    private IgniteCache<String,BinaryObject> ic ;
    public Transcation1EpPutScriptWork(EnterParam enterParam, IgniteCache<String, TranscationCache1> igniteCache, IgniteDataStreamer<String, TranscationCache1> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        this.ic = igniteCache.withKeepBinary() ;
    }

    @Override
    public long doing() {
        CustObjBuild<TranscationCache1> build = new CustObjBuild<>(TranscationCache1.class) ;
        Map<String,BinaryObject> map = new HashMap<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            TranscationCache1 obj = build.build1k(randomKey+"") ;
            map.put(obj.getId(), IgniteUtil.toBinary(obj)) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        Iterator<Map.Entry<String,BinaryObject>> it = map.entrySet().iterator() ;
        while (it.hasNext()){
            Map.Entry<String,BinaryObject> entry = it.next() ;
            ic.invoke(entry.getKey(),new PutEp1(),entry.getValue()) ;
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}