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
 * 随机写性能测试 1K 多表多次put
 */
public class AffinityStreamPutSmallScript {
    private Ignite ignite;
    private IgniteCache<String, AffinityMain> igniteCache;
    private IgniteCache<AffinityItemYesKey, AffinityItemYes> igniteCacheyes;
    private IgniteCache<String, AffinityItemNo> igniteCacheno;
    private IgniteDataStreamer<String, AffinityMain> igniteCacheS;
    private IgniteDataStreamer<AffinityItemYesKey, AffinityItemYes> igniteCacheyesS;
    private IgniteDataStreamer<String, AffinityItemNo> igniteCachenoS;
    private EnterParam enterParam;

    public AffinityStreamPutSmallScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
        AffinityMainConfiguration cfg = new AffinityMainConfiguration();
        AffinityItemYesConfiguration yescfg = new AffinityItemYesConfiguration();
        AffinityItemNoConfiguration nocfg = new AffinityItemNoConfiguration();
        //删除多表
        ignite.destroyCache(cfg.getCacheName());
        ignite.destroyCache(yescfg.getCacheName());
        ignite.destroyCache(nocfg.getCacheName());

        igniteCache = ignite.createCache(cfg.getCacheConfiguration());
        igniteCacheyes = ignite.createCache(yescfg.getCacheConfiguration());
        igniteCacheno = ignite.createCache(nocfg.getCacheConfiguration());
        igniteCacheS = ignite.dataStreamer(cfg.getCacheName());
        igniteCacheyesS = ignite.dataStreamer(yescfg.getCacheName());
        igniteCachenoS = ignite.dataStreamer(nocfg.getCacheName());
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
                AffinityStreamPutSmallScriptWork work = new AffinityStreamPutSmallScriptWork(enterParam, igniteCacheS, igniteCacheyesS, igniteCachenoS);
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
        enterParam.setLoop(1);
        System.out.println("affinity 导入小对象：" + enterParam.toString());
        AffinityStreamPutSmallScript scirpt = new AffinityStreamPutSmallScript(enterParam);
        scirpt.start();
    }

}
