package com.newland.boss.script.performance.index;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.entity.performance.affinity.AffinityItemNoItem;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/3/10.
 */
public class IndexPutScriptWork extends PerformanceScriptWork<String, AffinityItemNo> {
    public IndexPutScriptWork(EnterParam enterParam, IgniteCache<String, AffinityItemNo> igniteCache, IgniteDataStreamer<String, AffinityItemNo> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        CustObjBuild<AffinityItemNo> build = new CustObjBuild<>(AffinityItemNo.class) ;
        List<AffinityItemNo> list = new ArrayList<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            AffinityItemNo obj = build.build1k(randomKey+"") ;
            obj.setId2(randomKey);
            AffinityItemNoItem affinityItemNoItem = new AffinityItemNoItem(i+baseKey) ;
            obj.setItemNoItem(affinityItemNoItem);
            list.add(obj) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (AffinityItemNo obj:list) {
            igniteCache.put(obj.getId(),obj);
        }

        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}