package com.newland.boss.script.features;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 删除表
 */
public class DestoryScript extends BaseScript {
    private List<String> list ;

    public DestoryScript(String... cacheNames) {
        super(null);
        this.list = Arrays.asList(cacheNames);
    }

    @Override
    public void work() {
        if (list==null || list.size()==0){
            Collection<String> cacheList = ignite.cacheNames() ;
            cacheList.forEach(cacheName-> System.out.println("删除："+cacheName));
            ignite.destroyCaches(cacheList);
        }else{
            list.forEach(cacheName-> System.out.println("删除："+cacheName));
            ignite.destroyCaches(list);
        }
    }

    public static void main(String[] args) {
        //System.setProperty("IGNITE_SKIP_CONFIGURATION_CONSISTENCY_CHECK","true") ;
        DestoryScript destory = new DestoryScript() ;
        destory.start();
    }
}
