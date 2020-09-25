package com.newland.boss.script.performance.indexkey;

import com.newland.boss.entity.performance.affinity.*;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机读性能测试 4K 大对象Partition put
 */
public class IndexGetKeyIndexScript extends PerformanceScript<AffinityItemYesKey,AffinityItemYes> {
    IndexGetKeyIndexScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<AffinityItemYesKey,AffinityItemYes>> cz) {
        super(new AffinityItemYesConfiguration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("index getkey："+enterParam.toString());
        IndexGetKeyIndexScript scirpt = new IndexGetKeyIndexScript(enterParam,IndexGetKeyIndexScriptWork.class) ;
        scirpt.start();
    }

}
