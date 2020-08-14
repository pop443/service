package com.newland.boss.script.performance.transcation.simple;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.GetEp1;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class Transcation1EpGetScriptWork extends PerformanceScriptWork<String, TranscationCache1> {
    private IgniteCache<String,BinaryObject> ic ;
    public Transcation1EpGetScriptWork(EnterParam enterParam, IgniteCache<String, TranscationCache1> igniteCache, IgniteDataStreamer<String, TranscationCache1> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        this.ic = igniteCache.withKeepBinary() ;
    }

    @Override
    public long doing() {
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            ic.invoke(randomKey,new GetEp1()) ;
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}