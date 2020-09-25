package com.newland.boss.script.performance.indexkey;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.entity.performance.affinity.AffinityItemNoItem;
import com.newland.boss.entity.performance.affinity.AffinityItemYes;
import com.newland.boss.entity.performance.affinity.AffinityItemYesKey;
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
public class IndexPutScriptWork extends PerformanceScriptWork<AffinityItemYesKey,AffinityItemYes> {
    public IndexPutScriptWork(EnterParam enterParam, IgniteCache<AffinityItemYesKey,AffinityItemYes> igniteCache, IgniteDataStreamer<AffinityItemYesKey,AffinityItemYes> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<AffinityItemYes> build = new CustObjBuild<>(AffinityItemYes.class) ;
        Map<AffinityItemYesKey,AffinityItemYes> map = new HashMap<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            AffinityItemYes value = build.build1k(randomKey+"") ;
            AffinityItemYesKey key = new AffinityItemYesKey(randomKey,randomKey) ;
            map.put(key,value) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (Map.Entry<AffinityItemYesKey,AffinityItemYes> entry:map.entrySet()) {
            igniteCache.put(entry.getKey(),entry.getValue());
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}