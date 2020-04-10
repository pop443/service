package com.newland.boss.script.features.fuse;

import com.newland.boss.entity.fuse.FusePartitionOne;
import com.newland.boss.entity.fuse.FusePartitionOneConfiguration;
import com.newland.boss.script.features.BaseScript;

/**
 * 测试的脚本
 */
public class FusePartitionOneScript extends BaseScript<String, FusePartitionOne> {

    public FusePartitionOneScript() {
        super(new FusePartitionOneConfiguration());
    }

    @Override
    public void work() {
        String key1 = "0" ;
        String key2 = "1" ;
        String key3 = "2" ;
        FusePartitionOne one = igniteCache.get(key1) ;
        FusePartitionOne two = igniteCache.get(key2) ;
        FusePartitionOne three = igniteCache.get(key3) ;
        System.out.println(one);
        System.out.println(two);
        System.out.println(three);
    }

    public static void main(String[] args) {
        FusePartitionOneScript scirpt = new FusePartitionOneScript() ;
        scirpt.start();
    }

}
