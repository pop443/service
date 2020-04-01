package com.newland.boss.script.performance.cachestore.load;

import com.newland.boss.entity.performance.cachestore.CacheStore1;
import com.newland.boss.entity.performance.cachestore.CacheStore1Configuration;
import com.newland.boss.script.features.BaseScript;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class CacheStoreParallelLoadScript extends BaseScript<String,CacheStore1> {
    public CacheStoreParallelLoadScript() {
        super(new CacheStore1Configuration());
    }


    @Override
    protected void work() {
        igniteCache.clear();
        long l1 = System.currentTimeMillis() ;
        igniteCache.loadCache(null);
        long l2 = System.currentTimeMillis() ;
        System.out.println("并行耗时："+(l2-l1));
    }

    public static void main(String[] args) throws Exception{
        CacheStoreParallelLoadScript scirpt = new CacheStoreParallelLoadScript() ;
        scirpt.start();
    }
}
