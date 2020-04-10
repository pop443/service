package com.newland.boss.script.features.structure;

import com.newland.boss.script.features.BaseScript;
import com.newland.ignite.structure.ChangeData;
import com.newland.ignite.structure.ChangeDataConfiguration;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 测试的脚本
 */
public class ChangeDataPutScript extends BaseScript<String, ChangeData> {

    public ChangeDataPutScript() {
        super(new ChangeDataConfiguration());
    }

    @Override
    public void work() {
        String key ="31" ;
        ChangeData changeData = new ChangeData(key,1,1L,1D,new Timestamp(System.currentTimeMillis()),new Date(),key,key,key);
        igniteCache.put(key,changeData);
        System.out.println("--------put end ");
    }

    public static void main(String[] args) {
        ChangeDataPutScript scirpt = new ChangeDataPutScript() ;
        scirpt.start();
    }

}
