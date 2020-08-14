package com.newland.boss.script.performance.bulk.stringarray;

import com.newland.boss.entity.performance.bulk.StringObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;

import java.util.concurrent.*;

/**
 * 随机写性能测试 1K 多表多次put
 */
public class StringPutScript {
    private Ignite ignite;
    private IgniteCache<String, String[]> igniteCache ;
    private EnterParam enterParam;

    public StringPutScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
        this.enterParam = enterParam;
        StringObjConfiguration cfg = new StringObjConfiguration();
        igniteCache = ignite.getOrCreateCache(cfg.getCacheConfiguration()) ;
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
                StringPutScriptWork work = new StringPutScriptWork(enterParam, igniteCache,baseKey);
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
        System.out.println("byte[] Put ：" + enterParam.toString());
        StringPutScript scirpt = new StringPutScript(enterParam);
        scirpt.start();
    }

}
