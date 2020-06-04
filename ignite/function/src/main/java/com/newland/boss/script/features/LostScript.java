package com.newland.boss.script.features;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 删除表
 */
public class LostScript extends BaseScript {

    public LostScript() {
        super(null);
    }

    @Override
    public void work() {
        Collection<String> names = ignite.cacheNames() ;

        for (String name:names) {
            StringBuilder sb = new StringBuilder() ;
            sb.append("\r\n扫描").append(name).append("开始") ;
            ignite.cache(name).lostPartitions().forEach(index->{
                sb.append("；分区").append(index) ;
            });
            sb.append("\r\n扫描").append(name).append("结束") ;
            System.out.println(sb.toString());
        }

    }

    public static void main(String[] args) {
        //System.setProperty("IGNITE_SKIP_CONFIGURATION_CONSISTENCY_CHECK","true") ;
        LostScript destory = new LostScript() ;
        destory.start();
    }
}
