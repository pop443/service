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
        int index = 5 ;
        Map<String,ReplicatedMode> map = new HashMap<>() ;
        for (int i = 0; i < index; i++) {
            String key = i+"" ;
            map.put(key,new ReplicatedMode(key,key,i));
        }
        igniteCache.putAll(map);
        System.out.println(" >>> 插入"+index+"条全复制缓存数据");

        ReplicatedMode updateReplicated = new ReplicatedMode("1","22",22);
        igniteCache.put(updateReplicated.getId(),updateReplicated);
        System.out.println(" >>> 修改一条全复制缓存数据");
        while (true){
            print();
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    private void print(){
        System.out.println("输出 ");
        super.findAll().forEach(System.out::println);
    }

    public static void main(String[] args) {
        TestReplicatedModeScript scirpt = new TestReplicatedModeScript() ;
        scirpt.start();
    }  }
