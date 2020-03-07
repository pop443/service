package com.newland.boss.script.consistency;

import com.newland.boss.entity.consistency.PartitionedMode;
import com.newland.boss.entity.consistency.PartitionedModeConfiguration;
import com.newland.boss.script.BaseScript;

import java.util.HashMap;
import java.util.Map;

/**
 * 3.7.3 分区缓存的数据一致性
 */
public class TestPartitionedModeScript extends BaseScript<String,PartitionedMode> {
    public TestPartitionedModeScript() {
        super(new PartitionedModeConfiguration());
    }

    @Override
    public void work() {
        igniteCache.removeAll();
        int index = 10 ;

        Map<String,PartitionedMode> map = new HashMap<>() ;
        for (int i = 0; i < index; i++) {
            String key = i+"" ;
            map.put(key,new PartitionedMode(key,key,i));
        }
        igniteCache.putAll(map); ;
        System.out.println(" >>> 插入"+index+"条分区缓存数据");

        PartitionedMode demo1 = igniteCache.get("1") ;
        demo1.setName(demo1.getName()+demo1.getName());
        igniteCache.put(demo1.getId(),demo1); ;
        System.out.println(" >>> 修改一条分区缓存数据 使用DBeaver查询");
    }

    public static void main(String[] args) {
        TestPartitionedModeScript script = new TestPartitionedModeScript() ;
        script.start();
    }
}
