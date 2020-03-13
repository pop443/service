package com.newland.boss.script.performance.randomw.nearsmallput;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.obj.NearSmallCustObj;
import com.newland.boss.entity.performance.obj.NearSmallCustObjConfiguration;
import com.newland.boss.script.BaseScript;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.utils.Threads;

import java.util.concurrent.*;

/**
 * 随机写性能测试 1K 大小分区缓存 put
 */
public class NearSmallPutScript extends BaseScript<String,NearSmallCustObj> {
    private EnterParam enterParam;
    public NearSmallPutScript(EnterParam enterParam) {
        super(new NearSmallCustObjConfiguration());
        this.enterParam = enterParam ;
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    @Override
    public void work() {
        long holeTime = 0L ;
        for (int u = 0; u < enterParam.getLoop(); u++) {
            ExecutorService executorService = Executors.newFixedThreadPool(enterParam.getThreadNum()) ;
            BlockingQueue<Future<Long>> queue = new LinkedBlockingDeque<>(enterParam.getThreadNum());
            //实例化CompletionService
            CompletionService<Long> completionService = new ExecutorCompletionService<>(executorService, queue);
            for (int i = 0; i < enterParam.getThreadNum(); i++) {
                NearSmallPutScriptWork work = new NearSmallPutScriptWork(enterParam,igniteCache) ;
                completionService.submit(work);
            }
            long eachLoop = 0 ;
            try {
                for (int i = 0; i < enterParam.getThreadNum(); i++) {
                    Future<Long> future = completionService.take();
                    long time = future.get();
                    eachLoop = eachLoop+time ;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("第"+(u+1)+"次--总消耗"+eachLoop);
            //9分钟内关闭
            Threads.gracefulShutdown(executorService, 60, 10, TimeUnit.MINUTES);
            queue = null ;
            executorService = null ;
            completionService = null ;
            holeTime = holeTime + eachLoop ;
        }
        System.out.println("总消耗"+holeTime);
    }

    @Override
    protected void destory() {
        super.destory();

    }

    public static void main(String[] args) throws Exception{
        int count = Constant.count;
        int threadNum = 1 ;
        int batchSize = 1 ;
        int loop = 2 ;
        EnterParam enterParam = null ;

        if (args.length==2){
            count = Integer.parseInt(args[0]) ;
            threadNum = Integer.parseInt(args[1]) ;
            batchSize  = Integer.parseInt(args[2]) ;
            loop  = Integer.parseInt(args[3]) ;
            enterParam = new EnterParam(count,threadNum,batchSize,loop);
        }else if(args.length==0){
            enterParam = new EnterParam(count,threadNum,batchSize,loop);
        }else {
            throw new Exception("参数不对") ;
        }
        System.out.println("导入近缓存分区小对象数据量："+enterParam.toString());
        NearSmallPutScript scirpt = new NearSmallPutScript(enterParam) ;
        scirpt.start();
    }
}
