package com.newland.boss.script.features.structure;

import com.newland.boss.script.features.BaseScript;
import com.newland.ignite.structure.ChangeData;
import com.newland.ignite.structure.ChangeDataConfiguration;

import java.util.Iterator;

/**
 * 测试的脚本
 */
public class ChangeDataGetScript extends BaseScript<String, ChangeData> {

    public ChangeDataGetScript() {
        super(new ChangeDataConfiguration());
    }

    @Override
    public void work() {
        Iterable<ChangeData> iterable = super.findAll() ;
        Iterator<ChangeData> it = iterable.iterator() ;
        while (it.hasNext()){
            System.out.println("-----"+it.next());
        }
    }

    public static void main(String[] args) {
        ChangeDataGetScript scirpt = new ChangeDataGetScript() ;
        scirpt.start();
    }

}
