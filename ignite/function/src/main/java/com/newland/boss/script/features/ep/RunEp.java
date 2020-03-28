package com.newland.boss.script.features.ep;

import com.newland.boss.entity.ep.EpDemo;
import com.newland.boss.entity.ep.EpDemoConfiguration;
import com.newland.boss.script.features.BaseScript;
import com.newland.boss.script.features.TestCopysScript;
import com.newland.ignite.entryprocessor.NoopEp;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;

import java.util.Random;

/**
 * Created by xz on 2020/3/24.
 */
public class RunEp extends BaseScript<String,EpDemo> {
    public RunEp() {
        super(new EpDemoConfiguration());
    }
    @Override
    public void work() {
        IgniteCache<String,BinaryObject> ic = igniteCache.withKeepBinary();
        Random random = new Random() ;
        try {
            while (true){
                ic.invoke(random.nextInt(20)+"",new NoopEp());
                Thread.sleep(500L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RunEp scirpt = new RunEp() ;
        scirpt.start();
    }
}
