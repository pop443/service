package com.newland.boss.script.performance;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/13.
 */
public abstract class PerformanceScriptWork<K,V> implements Callable<Long> {
    protected EnterParam enterParam;
    protected IgniteCache<K,V> igniteCache ;
    protected IgniteDataStreamer<K,V> ids ;
    protected int baseKey ;

    public PerformanceScriptWork(EnterParam enterParam, IgniteCache<K, V> igniteCache, IgniteDataStreamer<K, V> ids,int baseKey) {
        this.enterParam = enterParam;
        this.igniteCache = igniteCache;
        this.ids = ids;
        this.baseKey = baseKey ;
    }

    @Override
    public Long call() throws Exception {
        return doing();
    }

    public abstract long doing() throws Exception;
}
