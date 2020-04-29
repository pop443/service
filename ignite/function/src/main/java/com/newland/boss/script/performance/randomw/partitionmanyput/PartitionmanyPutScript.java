package com.newland.boss.script.performance.randomw.partitionmanyput;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj2;
import com.newland.boss.entity.performance.obj.PartitionCustObj2Configuration;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;

import java.util.concurrent.*;

/**
 * 随机写性能测试 1K 多表多次put
 */
public class PartitionmanyPutScript {
    private Ignite ignite;
    private IgniteCache<String, PartitionCustObj> igniteCache1 ;
    private IgniteCache<String, PartitionCustObj2> igniteCache2 ;
    private EnterParam enterParam;

    public PartitionmanyPutScript(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
        this.enterParam = enterParam;
        PartitionCustObjConfiguration bigcfg = new PartitionCustObjConfiguration();
        PartitionCustObj2Configuration smallcfg = new PartitionCustObj2Configuration();
        igniteCache1 = ignite.getOrCreateCache(bigcfg.getCacheConfiguration()) ;
        igniteCache2 = ignite.getOrCreateCache(smallcfg.getCacheConfiguration());
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
                PartitionManyPutScriptWork work = new PartitionManyPutScriptWork(enterParam, igniteCache1, igniteCache2,baseKey);
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
        PartitionmanyPutScript scirpt = new PartitionmanyPutScript(enterParam);
        scirpt.start();
    }

}
