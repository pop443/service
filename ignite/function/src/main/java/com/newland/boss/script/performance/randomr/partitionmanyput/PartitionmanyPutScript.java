package com.newland.boss.script.performance.randomr.partitionmanyput;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.obj.PartitionBigCustObj;
import com.newland.boss.entity.performance.obj.PartitionBigCustObjConfiguration;
import com.newland.boss.entity.performance.obj.PartitionSmallCustObj;
import com.newland.boss.entity.performance.obj.PartitionSmallCustObjConfiguration;
import com.newland.boss.utils.Threads;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;

import java.util.concurrent.*;

/**
 * 随机读性能测试 1K 多表多次put
 */
public class PartitionmanyPutScript{
    private Ignite ignite ;
    private IgniteCache<String,PartitionBigCustObj> bigIgniteCache ;
    private IgniteCache<String,PartitionSmallCustObj> smallIgniteCache ;
    private int count ;
    private int threadNum ;
    private ExecutorService executorService ;
    public PartitionmanyPutScript(int count, int threadNum) {
        ignite = IgniteUtil.getIgnite() ;
        PartitionBigCustObjConfiguration bigcfg = new PartitionBigCustObjConfiguration() ;
        PartitionSmallCustObjConfiguration smallcfg = new PartitionSmallCustObjConfiguration() ;
        //删除多表
        ignite.destroyCache(bigcfg.getCacheName());
        ignite.destroyCache(smallcfg.getCacheName());

        bigIgniteCache = ignite.createCache(bigcfg.getCacheConfiguration()) ;
        smallIgniteCache = ignite.createCache(smallcfg.getCacheConfiguration()) ;

        this.count = count ;
        this.threadNum = threadNum ;
        executorService = Executors.newFixedThreadPool(threadNum) ;
    }

    public void start() {
        BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(threadNum);
        //实例化CompletionService
        CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
        int eachSize = count/threadNum ;
        System.out.println("线程数："+threadNum+";单线程随机读"+eachSize+"条;总数量："+count);
        for (int i = 0; i < threadNum; i++) {
            PartitionManyPutScriptWork work = new PartitionManyPutScriptWork(eachSize,count,bigIgniteCache,smallIgniteCache) ;
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
        destory();
    }

    protected void destory() {
        bigIgniteCache.close();
        smallIgniteCache.close();
        ignite.close();
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
        System.out.println("读取多表分区小对象数据量："+count+";线程："+threadNum);
        PartitionmanyPutScript scirpt = new PartitionmanyPutScript(count,threadNum) ;
        scirpt.start();
    }

}
