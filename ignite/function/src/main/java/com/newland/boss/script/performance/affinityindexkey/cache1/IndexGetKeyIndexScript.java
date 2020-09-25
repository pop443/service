package com.newland.boss.script.performance.affinityindexkey.cache1;

import com.newland.boss.entity.performance.affinity.AffinityItemYes;
import com.newland.boss.entity.performance.affinity.AffinityItemYesConfiguration;
import com.newland.boss.entity.performance.affinity.AffinityItemYesKey;
import com.newland.boss.entity.performance.affinitykey.Cache1CacheConfiguration;
import com.newland.boss.entity.performance.affinitykey.Cache1Key;
import com.newland.boss.entity.performance.affinitykey.Cache1Value;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机读性能测试 4K 大对象Partition put
 */
public class IndexGetKeyIndexScript extends PerformanceScript<Cache1Key,Cache1Value> {
    IndexGetKeyIndexScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<Cache1Key,Cache1Value>> cz) {
        super(new Cache1CacheConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("index getkey："+enterParam.toString());
        IndexGetKeyIndexScript scirpt = new IndexGetKeyIndexScript(enterParam,IndexGetKeyIndexScriptWork.class) ;
        scirpt.start();
    }

}
