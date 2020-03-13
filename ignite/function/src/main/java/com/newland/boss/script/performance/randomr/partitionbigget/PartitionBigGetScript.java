package com.newland.boss.script.performance.randomr.partitionbigget;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.obj.PartitionBigCustObj;
import com.newland.boss.entity.performance.obj.PartitionBigCustObjConfiguration;
import com.newland.boss.script.BaseScript;
import com.newland.boss.utils.Threads;

import java.util.concurrent.*;

/**
 * 随机读性能测试 4K 大对象Partition put
 */
public class PartitionBigGetScript extends BaseScript<String,PartitionBigCustObj> {
    private int count ;
    private int threadNum ;
    private ExecutorService executorService ;
    public PartitionBigGetScript(int count, int threadNum) {
        super(new PartitionBigCustObjConfiguration());
        this.count = count ;
        this.threadNum = threadNum ;
        executorService = Executors.newFixedThreadPool(threadNum) ;
    }


    @Override
    public void work() {
        BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(threadNum);
        //实例化CompletionService
        CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
        int eachSize = count/threadNum ;
        System.out.println("线程数："+threadNum+";单线程随机读"+eachSize+"条;总数量："+count);
        for (int i = 0; i < threadNum; i++) {
            PartitionBigGetScriptWork work = new PartitionBigGetScriptWork(eachSize,count,igniteCache) ;
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
        int batchSize = 1 ;
        if (args.length==3){
            threadNum = Integer.parseInt(args[0]) ;
            count  = Integer.parseInt(args[1]) ;
            batchSize = Integer.parseInt(args[2]) ;
        }else if(args.length==0){

        }else {
            throw new Exception("参数不对") ;
        }
        System.out.println("读分区大对象数据量："+count+";线程："+threadNum+",批次数量："+batchSize);
        PartitionBigGetScript scirpt = new PartitionBigGetScript(count,threadNum) ;
        scirpt.start();
    }

}