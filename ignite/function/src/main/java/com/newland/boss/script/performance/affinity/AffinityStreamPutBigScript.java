package com.newland.boss.script.performance.affinity;

import com.newland.boss.entity.performance.affinity.*;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.concurrent.*;

/**
 * 区间计算峰值值导入
 */
public class AffinityStreamPutBigScript {
    private Ignite ignite;
    private IgniteCache<String, AffinityMain> igniteCache;
    private IgniteCache<AffinityItemYesKey, AffinityItemYes> igniteCacheyes;
    private IgniteCache<String, AffinityItemNo> igniteCacheno;
    private IgniteDataStreamer<String, AffinityMain> igniteCacheS;
    private IgniteDataStreamer<AffinityItemYesKey, AffinityItemYes> igniteCacheyesS;
    private IgniteDataStreamer<String, AffinityItemNo> igniteCachenoS;
    private EnterParam enterParam;

    public AffinityStreamPutBigScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
        AffinityMainConfiguration cfg = new AffinityMainConfiguration();
        AffinityItemYesConfiguration yescfg = new AffinityItemYesConfiguration();
        AffinityItemNoConfiguration nocfg = new AffinityItemNoConfiguration();

        igniteCache = ignite.getOrCreateCache(cfg.getCacheConfiguration());
        igniteCacheyes = ignite.getOrCreateCache(yescfg.getCacheConfiguration());
        igniteCacheno = ignite.getOrCreateCache(nocfg.getCacheConfiguration());
        igniteCacheS = ignite.dataStreamer(cfg.getCacheName());
        igniteCacheS.allowOverwrite(true);
        igniteCacheyesS = ignite.dataStreamer(yescfg.getCacheName());
        igniteCacheS.allowOverwrite(true);
        igniteCachenoS = ignite.dataStreamer(nocfg.getCacheName());
        igniteCacheS.allowOverwrite(true);
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
                AffinityStreamPutBigScriptWork work = new AffinityStreamPutBigScriptWork(enterParam, igniteCacheS, igniteCacheyesS, igniteCachenoS);
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
        igniteCache.close();
        igniteCacheyes.close();
        igniteCacheno.close();
        igniteCacheS.close();
        igniteCacheyesS.close();
        igniteCachenoS.close();
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("affinity 导入大对象：" + enterParam.toString());
        AffinityStreamPutBigScript scirpt = new AffinityStreamPutBigScript(enterParam);
        scirpt.start();
    }

}
