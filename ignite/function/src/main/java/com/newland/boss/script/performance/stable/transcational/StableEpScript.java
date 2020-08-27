package com.newland.boss.script.performance.stable.transcational;

import com.newland.boss.entity.transcation.*;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 随机写性能测试 1K 多表多次put
 */
public class StableEpScript {
    private Ignite ignite;
    private IgniteCache<String, TranscationCache1> igniteCache1 ;
    private IgniteCache<String, TranscationCache2> igniteCache2 ;
    private IgniteCache<String, TranscationCache3> igniteCache3 ;
    private IgniteCache<String, TranscationCache4> igniteCache4 ;
    private IgniteCache<String, TranscationCache5> igniteCache5 ;
    private EnterParam enterParam;

    public StableEpScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
        this.enterParam = enterParam;
        TranscationCache1Configuration cfg1 = new TranscationCache1Configuration();
        TranscationCache2Configuration cfg2 = new TranscationCache2Configuration();
        TranscationCache3Configuration cfg3 = new TranscationCache3Configuration();
        TranscationCache4Configuration cfg4 = new TranscationCache4Configuration();
        TranscationCache5Configuration cfg5 = new TranscationCache5Configuration();
        igniteCache1 = ignite.getOrCreateCache(cfg1.getCacheConfiguration()) ;
        igniteCache2 = ignite.getOrCreateCache(cfg2.getCacheConfiguration());
        igniteCache3 = ignite.getOrCreateCache(cfg3.getCacheConfiguration()) ;
        igniteCache4 = ignite.getOrCreateCache(cfg4.getCacheConfiguration());
        igniteCache5 = ignite.getOrCreateCache(cfg5.getCacheConfiguration());
    }

    public void start() {
        List<IgniteCache> list = new ArrayList<>() ;
        list.add(igniteCache1) ;
        list.add(igniteCache2) ;
        list.add(igniteCache3) ;
        list.add(igniteCache4) ;
        list.add(igniteCache5) ;
        long holeTime = 0L;
        for (int u = 0; u < enterParam.getLoop(); u++) {
            ExecutorService executorService = Executors.newFixedThreadPool(enterParam.getThreadNum());
            BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(enterParam.getThreadNum());
            //实例化CompletionService
            CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
            for (int i = 0; i < enterParam.getThreadNum(); i++) {
                int baseKey = enterParam.getLoop()*enterParam.getThreadNum()*enterParam.getCount()*(enterParam.getIndex()-1)+(u+1)*enterParam.getThreadNum()*enterParam.getCount()+(i+1)*enterParam.getCount();

                StableEpScriptWork work = new StableEpScriptWork(enterParam, ignite,list,baseKey);
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
        StableEpScript scirpt = new StableEpScript(enterParam);
        scirpt.start();
    }

}
