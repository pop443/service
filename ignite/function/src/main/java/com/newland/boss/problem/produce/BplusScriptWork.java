package com.newland.boss.problem.produce;

import org.apache.ignite.IgniteCache;

import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class BplusScriptWork implements Callable<Long> {
    private int index;
    private IgniteCache<String, Bplus> igniteCache;

    public BplusScriptWork(int index, IgniteCache<String, Bplus> igniteCache) {
        this.index = index;
        this.igniteCache = igniteCache;
    }

    @Override
    public Long call() throws Exception {
        Long l1 = System.currentTimeMillis();
        try {
            working();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long l2 = System.currentTimeMillis();
        return l2 - l1;
    }

    private void working() {
        int i = 1;
        while (true) {
                Bplus bplus = new Bplus("hell0");
                igniteCache.put(i++ + "", bplus);
        }


    }
}
