package com.newland.boss.script.features.fuse;

import com.newland.boss.entity.fuse.FusePartitionTwo;
import com.newland.boss.entity.fuse.FusePartitionTwoConfiguration;
import com.newland.boss.script.features.BaseScript;

/**
 * 测试的脚本
 */
public class FusePartitionTwoScript extends BaseScript<String, FusePartitionTwo> {

    public FusePartitionTwoScript() {
        super(new FusePartitionTwoConfiguration());
    }

    @Override
    public void work() {
        String key1 = "0" ;
        String key2 = "1" ;
        String key3 = "2" ;
        FusePartitionTwo one = igniteCache.get(key1) ;
        FusePartitionTwo two = igniteCache.get(key2) ;
        FusePartitionTwo three = igniteCache.get(key3) ;
        System.out.println(one);
        System.out.println(two);
        System.out.println(three);
    }

    public static void main(String[] args) {
        FusePartitionTwoScript scirpt = new FusePartitionTwoScript() ;
        scirpt.start();
    }

}
