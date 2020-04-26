package com.newland.boss.script.performance.failover.level3;

import com.newland.boss.entity.performance.failover.PartitionLevel3;
import com.newland.boss.entity.performance.failover.PartitionLevel3Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class PartitionLevel3PutStreamScript extends PerformanceScript<String,PartitionLevel3> {
    PartitionLevel3PutStreamScript(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, PartitionLevel3>> cz) {
        super(new PartitionLevel3Configuration(), enterParam, cz);
    }


    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("0ms间隔10M数据量 put stream："+enterParam.toString());
        PartitionLevel3PutStreamScript scirpt = new PartitionLevel3PutStreamScript(enterParam,PartitionLevel3PutStreamScriptWork.class) ;
        scirpt.start();
    }
}
