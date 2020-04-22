package com.newland.boss.script.performance.randomwr.partitionmanypg;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj2;
import com.newland.boss.entity.performance.obj.PartitionCustObj2Configuration;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.randomr.partitionmanyget.PartitionManyGetScriptWork;
import com.newland.boss.script.performance.randomw.partitionmanyput.PartitionManyPutScriptWork;
import com.newland.boss.script.performance.randomw.partitionmanyput.PartitionmanyPutScript;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;

import java.util.concurrent.*;

/**
 * Created by xz on 2020/3/14.
 */
public class PartitionManyPGScript_3 {
    private Ignite ignite;
    private PartitionCustObjConfiguration bigcfg = new PartitionCustObjConfiguration(1);
    private PartitionCustObj2Configuration smallcfg = new PartitionCustObj2Configuration(2);
    private EnterParam enterParam;

    public PartitionManyPGScript_3(EnterParam enterParam) {
        ignite = IgniteUtil.getIgnite();
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
                IgniteCache<String, PartitionCustObj> igniteCache1= ignite.getOrCreateCache(bigcfg.getCacheConfiguration());
                IgniteCache<String, PartitionCustObj2> igniteCache2= ignite.getOrCreateCache(smallcfg.getCacheConfiguration());;
                PartitionManyPutScriptWork work1 = new PartitionManyPutScriptWork(enterParam, igniteCache1, igniteCache2,ignite);
                PartitionManyGetScriptWork work2 = new PartitionManyGetScriptWork(enterParam, igniteCache1, igniteCache2);
                completionService.submit(work1);
                completionService.submit(work2);
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
        //igniteCache1.close();
        //igniteCache2.close();
        ignite.close();
    }

    public static void main(String[] args) throws Exception {
        EnterParam enterParam = EnterParam.getEnterParam(args);
        enterParam.setBatchSize(5);
        System.out.println("EP put(多笔数据)：" + enterParam.toString());
        PartitionmanyPutScript scirpt = new PartitionmanyPutScript(enterParam);
        scirpt.start();
    }
}
