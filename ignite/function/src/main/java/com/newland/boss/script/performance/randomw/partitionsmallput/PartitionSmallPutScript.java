package com.newland.boss.script.performance.randomw.partitionsmallput;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.obj.PartitionSmallCustObj;
import com.newland.boss.entity.performance.obj.PartitionSmallCustObjConfiguration;
import com.newland.boss.script.BaseScript;
import com.newland.boss.utils.Threads;

import java.util.concurrent.*;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class PartitionSmallPutScript extends BaseScript<String,PartitionSmallCustObj> {
    private int count ;
    private int threadNum ;
    private ExecutorService executorService ;
    public PartitionSmallPutScript(int count, int threadNum) {
        super(new PartitionSmallCustObjConfiguration());
        this.count = count ;
        this.threadNum = threadNum ;
        executorService = Executors.newFixedThreadPool(threadNum) ;
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    @Override
    public void work() {
        BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(threadNum);
        //实例化CompletionService
        CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
        int eachSize = count/threadNum ;
        System.out.println("线程数："+threadNum+";单线程随机写"+eachSize+"条;总数量："+count);
        for (int i = 0; i < threadNum; i++) {
            PartitionSmallPutScriptWork work = new PartitionSmallPutScriptWork(eachSize,count,igniteCache) ;
            completionService.submit(work);
        }
        long holeTime = 0 ;
        try {
            for (int i = 0; i < threadNum; i++) {
                Future<Long> future = completionService.take();
                long time = future.get();
                System.out.println("消耗"+time);
                holeTime = holeTime+time ;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("总消耗"+holeTime);
    }

    @Override
    protected void destory() {
        super.destory();
        if (executorService != null) {
            //9分钟内关闭
            Threads.gracefulShutdown(executorService, 60, 10, TimeUnit.MINUTES);
        }
    }

    public static void main(String[] args) throws Exception{
        int count = Constant.count;
        int threadNum = 1 ;
        if (args.length==2){
            threadNum = Integer.parseInt(args[0]) ;
            count  = Integer.parseInt(args[1]) ;
        }else if(args.length==0){

        }else {
            throw new Exception("参数不对") ;
        }
        System.out.println("导入分区小对象数据量："+count+";线程："+threadNum);
        PartitionSmallPutScript scirpt = new PartitionSmallPutScript(count,threadNum) ;
        scirpt.start();
    }

}
