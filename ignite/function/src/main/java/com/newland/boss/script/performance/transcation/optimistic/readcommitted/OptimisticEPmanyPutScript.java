package com.newland.boss.script.performance.transcation.optimistic.readcommitted;

import com.newland.boss.entity.transcation.TranscationCache1;
import com.newland.boss.entity.transcation.TranscationCache1Configuration;
import com.newland.boss.entity.transcation.TranscationCache2;
import com.newland.boss.entity.transcation.TranscationCache2Configuration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;

import java.util.concurrent.*;

/**
 * 随机写性能测试 1K 多表多次put
 */
public class OptimisticEPmanyPutScript {
    private Ignite ignite;
    private IgniteCache<String, TranscationCache1> igniteCache1 ;
    private IgniteCache<String, TranscationCache2> igniteCache2 ;
    private EnterParam enterParam;

    public OptimisticEPmanyPutScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
        this.enterParam = enterParam;
        TranscationCache1Configuration bigcfg = new TranscationCache1Configuration();
        TranscationCache2Configuration smallcfg = new TranscationCache2Configuration();
        igniteCache1 = ignite.getOrCreateCache(bigcfg.getCacheConfiguration()) ;
        igniteCache2 = ignite.getOrCreateCache(smallcfg.getCacheConfiguration());
    }

    public void start() {
        long holeTime = 0L;
        for (int u = 0; u < enterParam.getLoop(); u++) {
            ExecutorService executorService = Executors.newFixedThreadPool(enterParam.getThreadNum());
            BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(enterParam.getThreadNum());
            //实例化CompletionService
            CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
            for (int i = 0; i < enterParam.getThreadNum(); i++) {
                int baseKey = enterParam.getLoop()*enterParam.getThreadNum()*enterParam.getCount()*(enterParam.getIndex()-1)+(u+1)*enterParam.getThreadNum()*enterParam.getCount()+(i+1)*enterParam.getCount();
                OptimisticEPmanyPutScriptWork work = new OptimisticEPmanyPutScriptWork(enterParam, ignite,igniteCache1, igniteCache2,baseKey);
                completionService.submit(work);
            }
            long eachLoop = 0;
            try {
                for (int i = 0; i < enterParam.getThreadNum(); i++) {
                    Future<Long> future = completionService.take();
                    long time = future.get();
                    System.out.println(time);
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
        //igniteCache1.close();
        //igniteCache2.close();
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("PartitionmanyPut ：" + enterParam.toString());
        OptimisticEPmanyPutScript scirpt = new OptimisticEPmanyPutScript(enterParam);
        scirpt.start();
    }

}
