package com.newland.boss.script;

import java.util.Arrays;
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
            ignite.destroyCaches(ignite.cacheNames());
        }else{
            ignite.destroyCaches(list);
        }
    }

    public static void main(String[] args) {
        //System.setProperty("IGNITE_SKIP_CONFIGURATION_CONSISTENCY_CHECK","true") ;
        DestoryScript destory = new DestoryScript() ;
        destory.start();
    }
}
