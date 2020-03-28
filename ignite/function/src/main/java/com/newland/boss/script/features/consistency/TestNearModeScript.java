package com.newland.boss.script.features.consistency;

import com.newland.boss.entity.consistency.NearMode;
import com.newland.boss.entity.consistency.NearModeConfiguration;
import com.newland.boss.script.features.BaseScript;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 3.7.2 近区缓存的数据一致性
 */
public class TestNearModeScript extends BaseScript<String,NearMode> {
    public TestNearModeScript() {
        super(new NearModeConfiguration());
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    @Override
    public void work() {
        int index = 2 ;
        Map<String,NearMode> map = new HashMap<>() ;
        for (int i = 0; i < index; i++) {
            String key = i+"" ;
            map.put(key,new NearMode(key,key,i));
        }
        igniteCache.putAll(map);
        System.out.println(" >>> 插入"+index+"条近缓存数据");
        print();
        System.out.println(" >>> 修改第一条条近缓存数据");
        NearMode updateNear = new NearMode("1","22",22);
        igniteCache.put(updateNear.getId(),updateNear);
        print();

    }
    private void print(){
        System.out.println("输出 ");
        super.findAll().forEach(System.out::println);
    }

    public static void main(String[] args) {
        TestNearModeScript scirpt = new TestNearModeScript() ;
        scirpt.start();
    }
}
