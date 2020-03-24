package com.newland.boss.script.features.ep;

import com.newland.boss.entity.ep.EpDemo;
import com.newland.boss.entity.ep.EpDemoConfiguration;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj2;
import com.newland.boss.entity.performance.obj.PartitionCustObj2Configuration;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;

import java.util.concurrent.*;

/**
 * 随机写性能测试 1K 多表多次put
 */
public class EpScript {
    private Ignite ignite;
    private IgniteCache<String, EpDemo> igniteCache1;
    private EnterParam enterParam;

    public EpScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
        EpDemoConfiguration bigcfg = new EpDemoConfiguration();
        //删除多表
        ignite.destroyCache(bigcfg.getCacheName());
        igniteCache1 = ignite.createCache(bigcfg.getCacheConfiguration());
        igniteCache1.put("1",new EpDemo("1",0));
        this.enterParam = enterParam;
    }

    public void start() {
        int threadNum = 4;
        IgniteCache<String, BinaryObject> ic = igniteCache1.withKeepBinary();
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(enterParam.getCount());
        //实例化CompletionService
        CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
        for (int i = 0; i < enterParam.getCount(); i++) {
            EpScriptWork work = new EpScriptWork(i+1,ic);
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
        igniteCache1.close();
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("EP cas：" + enterParam.toString());
        EpScript scirpt = new EpScript(enterParam);
        scirpt.start();
    }

}
