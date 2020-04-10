package com.newland.boss.script.features.fuse;

import com.newland.boss.entity.fuse.FuseReplicated;
import com.newland.boss.entity.fuse.FuseReplicatedConfiguration;
import com.newland.boss.script.features.BaseScript;

/**
 * 测试的脚本
 */
public class FuseReplicatedScript extends BaseScript<String, FuseReplicated> {

    public FuseReplicatedScript() {
        super(new FuseReplicatedConfiguration());
    }

    @Override
    public void work() {
        String key1 = "0" ;
        String key2 = "1" ;
        String key3 = "2" ;
        FuseReplicated one = igniteCache.get(key1) ;
        FuseReplicated two = igniteCache.get(key2) ;
        FuseReplicated three = igniteCache.get(key3) ;
        System.out.println(one);
        System.out.println(two);
        System.out.println(three);
    }

    public static void main(String[] args) {
        FuseReplicatedScript scirpt = new FuseReplicatedScript() ;
        scirpt.start();
    }

}
