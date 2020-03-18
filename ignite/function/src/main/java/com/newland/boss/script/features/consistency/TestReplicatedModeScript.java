package com.newland.boss.script.features.consistency;

import com.newland.boss.entity.consistency.ReplicatedMode;
import com.newland.boss.entity.consistency.ReplicatedModeConfiguration;
import com.newland.boss.script.features.BaseScript;

import java.util.HashMap;
import java.util.Map;

/**
 * 3.7.1 全复制的数据一致性
 */
public class TestReplicatedModeScript extends BaseScript<String,ReplicatedMode> {
    public TestReplicatedModeScript() {
        super(new ReplicatedModeConfiguration());
    }

    @Override
    public void work() {
        igniteCache.removeAll();
        int index = 10 ;

        Map<String,ReplicatedMode> map = new HashMap<>() ;
        for (int i = 0; i < index; i++) {
            String key = i+"" ;
            map.put(key,new ReplicatedMode(key,key,i));
        }
        igniteCache.putAll(map); ;
        System.out.println(" >>> 插入"+index+"条全复制缓存数据");

        ReplicatedMode demo1 = igniteCache.get("1") ;
        demo1.setName(demo1.getName()+demo1.getName());
        igniteCache.put(demo1.getId(),demo1); ;
        System.out.println(" >>> 修改一条全复制缓存 使用DBeaver查询");

    }

    public static void main(String[] args) {
        TestReplicatedModeScript scirpt = new TestReplicatedModeScript() ;
        scirpt.start();
    }  }
