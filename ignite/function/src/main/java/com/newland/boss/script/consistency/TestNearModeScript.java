package com.newland.boss.script.consistency;

import com.newland.boss.entity.consistency.NearMode;
import com.newland.boss.entity.consistency.NearModeConfiguration;
import com.newland.boss.script.BaseScript;

import java.util.HashMap;
import java.util.Map;

/**
 * 3.7.2 近区缓存的数据一致性
 */
public class TestNearModeScript extends BaseScript<String,NearMode> {
    public TestNearModeScript() {
        super(new NearModeConfiguration());
    }

    @Override
    public void work() {
        igniteCache.removeAll();
        int index = 10 ;

        Map<String,NearMode> map = new HashMap<>() ;
        for (int i = 0; i < index; i++) {
            String key = i+"" ;
            map.put(key,new NearMode(key,key,i));
        }
        igniteCache.putAll(map);
        System.out.println(" >>> 插入"+index+"条近缓存数据");

        NearMode demo1 = igniteCache.get("1") ;
        demo1.setName(demo1.getName()+demo1.getName());
        igniteCache.put(demo1.getId(),demo1); ;
        System.out.println(" >>> 修改一条近缓存数据 使用DBeaver查询");

    }

    public static void main(String[] args) {
        TestNearModeScript scirpt = new TestNearModeScript() ;
        scirpt.start();
    }
}
