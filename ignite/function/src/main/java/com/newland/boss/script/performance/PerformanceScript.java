package com.newland.boss.script.performance;

import com.newland.boss.script.features.BaseScript;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.CustCacheConfiguration;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.lang.reflect.Constructor;
import java.util.concurrent.*;

/**
 * Created by xz on 2020/3/13.
 */
public class PerformanceScript<K,V> extends BaseScript<K,V>{
    private int backup ;
    private EnterParam enterParam;
    private Class<? extends PerformanceScriptWork<K,V>>[] czs;
    public PerformanceScript(CustCacheConfiguration<K, V> cfg,EnterParam enterParam,Class<? extends PerformanceScriptWork<K,V>>... cz) {
        super(cfg);
        this.enterParam = enterParam ;
        this.czs = cz ;
    }

    @Override
    protected void work() {
        long holeTime = 0L ;
        for (int u = 0; u < enterParam.getLoop(); u++) {
            int threadNum = enterParam.getThreadNum()* czs.length;
            ExecutorService executorService = Executors.newFixedThreadPool(threadNum) ;
            BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(threadNum);
            //实例化CompletionService
            CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
            long eachLoop = 0 ;
            try {
                for (int i = 0; i < threadNum; i++) {
                    for (Class<? extends PerformanceScriptWork<K,V>> cz:czs) {
                        Constructor<? extends PerformanceScriptWork<K,V>> constructor = cz.getConstructor(EnterParam.class,IgniteCache.class,IgniteDataStreamer.class) ;
                        PerformanceScriptWork<K,V> performanceScriptWork = constructor.newInstance(enterParam,igniteCache,getIgniteDataStreamer());
                        completionService.submit(performanceScriptWork);
                    }
                }
                for (int i = 0; i < threadNum; i++) {
                    Future<Long> future = completionService.take();
                    long time = future.get();
                    eachLoop = eachLoop+time ;
                }
                System.out.println("第"+(u+1)+"次--总消耗"+eachLoop);
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
}
