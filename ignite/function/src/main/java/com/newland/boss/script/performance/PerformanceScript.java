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
    protected EnterParam enterParam;
    protected Class<? extends PerformanceScriptWork<K,V>>[] czs;
    public PerformanceScript(CustCacheConfiguration<K, V> cfg,EnterParam enterParam,Class<? extends PerformanceScriptWork<K,V>>... cz) {
        super(cfg);
        this.enterParam = enterParam ;
        this.czs = cz ;
    }

    @Override
    protected void work() {
        long holeTime = 0L ;
        int base3 = (enterParam.getIndex() - 1) * enterParam.getCount() * enterParam.getThreadNum() * enterParam.getLoop();
        for (int u = 0; u < enterParam.getLoop(); u++) {
            int base2 = u  * enterParam.getCount() * enterParam.getThreadNum() + base3;
            int allThreadNum = enterParam.getThreadNum()* czs.length;
            ExecutorService executorService = Executors.newFixedThreadPool(allThreadNum) ;
            BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(allThreadNum);
            //实例化CompletionService
            CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
            long eachLoop = 0 ;
            try {
                for (int i = 0; i < enterParam.getThreadNum(); i++) {
                    int baseKey = i * enterParam.getCount()+base2;
                    for (Class<? extends PerformanceScriptWork<K,V>> cz:czs) {
                        Constructor<? extends PerformanceScriptWork<K,V>> constructor = cz.getConstructor(EnterParam.class,IgniteCache.class,IgniteDataStreamer.class,Integer.class) ;
                        //PerformanceScriptWork<K,V> performanceScriptWork = constructor.newInstance(enterParam,ignite.getOrCreateCache(cfg.getCacheConfiguration()),getIgniteDataStreamer(),baseKey);
                        PerformanceScriptWork<K,V> performanceScriptWork = constructor.newInstance(enterParam,igniteCache,getIgniteDataStreamer(),baseKey);

                        completionService.submit(performanceScriptWork);
                    }
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
}
