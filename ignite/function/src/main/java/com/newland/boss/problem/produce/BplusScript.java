package com.newland.boss.problem.produce;

import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;

import java.util.concurrent.*;

public class BplusScript {
    private Ignite ignite;
    private IgniteCache<String, Bplus> igniteCache;
    private EnterParam enterParam;

    public BplusScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
        BplusConfiguration cfg = new BplusConfiguration();
        igniteCache = ignite.getOrCreateCache(cfg.getCacheConfiguration());
        this.enterParam = enterParam;
    }

    public void start() {
        int threadNum = enterParam.getThreadNum();
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(enterParam.getCount());
        //实例化CompletionService
        CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
        for (int i = 0; i < enterParam.getCount(); i++) {
            BplusScriptWork work = new BplusScriptWork(i+1,igniteCache);
            completionService.submit(work);
        }
        long eachLoop = 0;
        try {
            for (int i = 0; i < enterParam.getCount(); i++) {
                Future<Long> future = completionService.take();
                long time = future.get();
                eachLoop = eachLoop + time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //9分钟内关闭
        Threads.gracefulShutdown(executorService, 60, 10, TimeUnit.MINUTES);
        queue = null;
        executorService = null;
        completionService = null;
        destory();
    }

    protected void destory() {
        igniteCache.close();
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("bplus：" + enterParam.toString());
        BplusScript scirpt = new BplusScript(enterParam);
        scirpt.start();
    }

}
