package com.newland.boss.script.performance;

import com.newland.boss.script.BaseScript;
import com.newland.boss.script.performance.randomr.nearsmallget.NearSmallGetScriptWork;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.CustCacheConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

/**
 * Created by xz on 2020/3/13.
 */
public class PerformanceScript<K,V> extends BaseScript<K,V>{
    private EnterParam enterParam;
    private Class<? extends PerformanceScriptWork<K,V>> cz;
    public PerformanceScript(CustCacheConfiguration<K, V> cfg,EnterParam enterParam,Class<? extends PerformanceScriptWork<K,V>> cz) {
        super(cfg);
        this.enterParam = enterParam ;
        this.cz = cz ;
    }

    @Override
    protected void work() {
        long holeTime = 0L ;
        for (int u = 0; u < enterParam.getLoop(); u++) {
            ExecutorService executorService = Executors.newFixedThreadPool(enterParam.getThreadNum()) ;
            BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(enterParam.getThreadNum());
            //实例化CompletionService
            CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
            long eachLoop = 0 ;
            try {
                for (int i = 0; i < enterParam.getThreadNum(); i++) {
                    Constructor<? extends PerformanceScriptWork<K,V>> constructor = cz.getConstructor(EnterParam.class,IgniteCache.class,IgniteDataStreamer.class) ;
                    PerformanceScriptWork<K,V> performanceScriptWork = constructor.newInstance(enterParam,igniteCache,getIgniteDataStreamer());
                    completionService.submit(performanceScriptWork);
                }
                for (int i = 0; i < enterParam.getThreadNum(); i++) {
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
