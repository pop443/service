package com.newland.boss.script.performance.cachestore;

import com.newland.boss.entity.performance.cachestore.CacheStore1;
import com.newland.boss.entity.performance.cachestore.CacheStore1Configuration;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj2;
import com.newland.boss.entity.performance.obj.PartitionCustObj2Configuration;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionmanyput.PartitionManyPutScriptWork;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;

import java.util.concurrent.*;

/**
 *
 */
public class CacheStoreNoWBPutScript {
    private Ignite ignite;
    private IgniteCache<String, CacheStore1> igniteCache1;
    private EnterParam enterParam;

    public CacheStoreNoWBPutScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgniteByXml("node-config-manyDS.xml");
        CacheStore1Configuration bigcfg = new CacheStore1Configuration();
        igniteCache1 = ignite.getOrCreateCache(bigcfg.getCacheConfiguration());
        this.enterParam = enterParam;
    }

    public void start() {
        long holeTime = 0L;
        for (int u = 0; u < enterParam.getLoop(); u++) {
            ExecutorService executorService = Executors.newFixedThreadPool(enterParam.getThreadNum());
            BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(enterParam.getThreadNum());
            //实例化CompletionService
            CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
            for (int i = 0; i < enterParam.getThreadNum(); i++) {
                int baseKey = enterParam.getLoop()*enterParam.getThreadNum()*enterParam.getCount()*enterParam.getIndex()+(u+1)*enterParam.getThreadNum()*enterParam.getCount()+(i+1)*enterParam.getCount();
                CacheStoreNoWBPutScriptWork work = new CacheStoreNoWBPutScriptWork(enterParam, igniteCache1,null,baseKey);
                completionService.submit(work);
            }
            long eachLoop = 0;
            try {
                for (int i = 0; i < enterParam.getThreadNum(); i++) {
                    Future<Long> future = completionService.take();
                    long time = future.get();
                    eachLoop = eachLoop + time;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("第" + (u + 1) + "次--总消耗" + eachLoop);
            //9分钟内关闭
            Threads.gracefulShutdown(executorService, 60, 10, TimeUnit.MINUTES);
            queue = null;
            executorService = null;
            completionService = null;
            holeTime = holeTime + eachLoop;
        }
        System.out.println("总消耗" + holeTime);
        destory();
    }

    protected void destory() {
        igniteCache1.close();
        ignite.close();
    }


    public static void main(String[] args) throws Exception{
        System.setProperty("druid.logType","log4j2");
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("cachestore put："+enterParam.toString());
        CacheStoreNoWBPutScript scirpt = new CacheStoreNoWBPutScript(enterParam) ;
        scirpt.start();
    }
}
