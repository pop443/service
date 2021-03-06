package com.newland.boss.script.performance.failover.level1;

import com.newland.boss.entity.performance.failover.PartitionLevel1;
import com.newland.boss.entity.performance.failover.PartitionLevel1Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class PartitionLevel1PutScript extends PerformanceScript<String,PartitionLevel1> {
    PartitionLevel1PutScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionLevel1>> cz) {
        super(new PartitionLevel1Configuration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("0ms间隔1M数据量 put："+enterParam.toString());
        PartitionLevel1PutScript scirpt = new PartitionLevel1PutScript(enterParam,PartitionLevel1PutScriptWork.class) ;
        scirpt.start();
    }
}
