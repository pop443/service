package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import com.newland.boss.script.features.BaseScript;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.utils.Threads;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.cachestore.entity.ExpiryConfiguration;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.lang.reflect.Constructor;
import java.util.concurrent.*;

/**
 * Created by xz on 2020/4/30.
 */
public class AllTest extends BaseScript<String, Expiry> {
    private EnterParam enterParam ;
    public AllTest(EnterParam enterParam) {
        super(new ExpiryConfiguration());
        this.enterParam = enterParam ;
    }

    @Override
    protected void work() {
        long holeTime = 0L ;
        for (int u = 0; u < enterParam.getLoop(); u++) {
            int allThreadNum = enterParam.getThreadNum();
            ExecutorService executorService = Executors.newFixedThreadPool(allThreadNum) ;
            BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(allThreadNum);
            //实例化CompletionService
            CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
            long eachLoop = 0 ;
            try {
                for (int i = 0; i < enterParam.getThreadNum(); i++) {
                    int baseKey = enterParam.getLoop()*enterParam.getThreadNum()*enterParam.getCount()*(enterParam.getIndex()-1)+(u+1)*enterParam.getThreadNum()*enterParam.getCount()+(i+1)*enterParam.getCount();
                    AllTestWork allTestWork = new AllTestWork(enterParam,ignite.cache(cfg.getCacheName()),ignite.dataStreamer(cfg.getCacheName()),baseKey);
                    completionService.submit(allTestWork);
                }
                for (int i = 0; i < allThreadNum; i++) {
                    Future<Long> future = completionService.take();
                    long time = future.get();
                    eachLoop = eachLoop+time ;
                    System.out.println("单线程耗时："+time);
                }
                System.out.println("第"+(u+1)+"次循环--总消耗"+eachLoop);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //9分钟内关闭
            Threads.gracefulShutdown(executorService, 60, 10, TimeUnit.MINUTES);
            queue = null ;
            executorService = null ;
            completionService = null ;
            holeTime = holeTime + eachLoop ;
        }
        System.out.println("总消耗"+holeTime);
    }

    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        enterParam.setThreadNum(1);
        enterParam.setLoop(20000);
        System.out.println("EPtest：" + enterParam.toString());
        AllTest scirpt = new AllTest(enterParam);
        scirpt.start();
    }
}
