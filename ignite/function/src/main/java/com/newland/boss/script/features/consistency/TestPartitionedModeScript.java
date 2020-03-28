package com.newland.boss.script.features.consistency;

import com.newland.boss.entity.consistency.PartitionedMode;
import com.newland.boss.entity.consistency.PartitionedModeConfiguration;
import com.newland.boss.script.features.BaseScript;

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
        int index = 5 ;
        Map<String,PartitionedMode> map = new HashMap<>() ;
        for (int i = 0; i < index; i++) {
            String key = i+"" ;
            map.put(key,new PartitionedMode(key,key,i));
        }
        igniteCache.putAll(map);
        System.out.println(" >>> 插入"+index+"条分区缓存数据");

        PartitionedMode updateReplicated = new PartitionedMode("1","22",22);
        igniteCache.put(updateReplicated.getId(),updateReplicated);
        System.out.println(" >>> 修改一条分区缓存数据");
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
        TestPartitionedModeScript script = new TestPartitionedModeScript() ;
        script.start();
    }
}
