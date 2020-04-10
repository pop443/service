package com.newland.ignite.label.prepare;

import com.newland.ignite.utils.CustCacheConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2019/12/27.
 */
public abstract class BaseUpload<K,V> {

    private Long pageSize;
    private Long allSize;
    private Integer threadSize;
    private ExecutorService executorService;

    public BaseUpload(Long allSize) {
        this(2000L, allSize);
    }

    public BaseUpload(Long pageSize, Long allSize) {
        this.pageSize = pageSize;
        this.allSize = allSize;
        this.threadSize = 5;
        executorService = Executors.newFixedThreadPool(threadSize);
    }

    public static void main(String[] args) {
        Long l1  = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long l2  = System.currentTimeMillis();
        System.out.println(l2-l1);
    }
    //普通插入
    public void start(Ignite ignite, CustCacheConfiguration<K, V> custCfg) {
        System.out.println(" ------------- start -------------");
        IgniteDataStreamer<K, V> stmr = custCfg.getDataStreamer(ignite);
        stmr.keepBinary(true);

        Long partSize = allSize / threadSize;
        BlockingQueue<Future<String>> queue = new LinkedBlockingDeque<>(threadSize);
        //实例化CompletionService
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService, queue);
        for (int i = 0; i < threadSize; i++) {
            int index = i ;
            completionService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {

                    long l1 = System.currentTimeMillis();
                    Map<K, V> map = new HashMap<>();
                    for (long i = 1; i <= partSize; i++) {
                        V v = getBean();
                        map.put(getKey(v), v);
                        if (i % pageSize == 0) {
                            submit(map, stmr);
                            map.clear();
                            System.out.println(System.currentTimeMillis()+"--index:"+i+"--partSize:"+partSize);
                        }
                    }
                    if (map.size() > 0) {
                        submit(map, stmr);
                        map.clear();
                    }
                    long l2 = System.currentTimeMillis();
                    return "第"+index+"线程:处理"+partSize+"数据--cost:" + (l2 - l1);
                }
            });
        }

        try {
            for (int i = 0; i < threadSize; i++) {
                Future<String> future = completionService.take();
                String str = future.get();
                System.out.println(str);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(" ------------- end -------------");
        stmr.close();
        end();
        custCfg.close();
        IgniteUtil.release(ignite);
    }

    private void submit(Map<K, V> map, IgniteDataStreamer<K, V> stmr) {
        stmr.addData(map);
        stmr.flush();
    }

    private void end() {
        if (executorService != null) {
            //30秒内关闭
            gracefulShutdown(executorService, 60, 30, TimeUnit.SECONDS);
        }

    }

    private static void gracefulShutdown(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout, TimeUnit timeUnit) {
        pool.shutdown(); // 停止接收新任务并尝试完成所有已存在任务>
        try {
            // 停止接收新任务并尝试完成所有已存在任务
            if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
                pool.shutdownNow(); // 如果超时, 则调用shutdownNow, 取消在workQueue中Pending的任务,并中断所有阻塞函数
                // 等待取消的回应
                if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
                    System.err.println("线程池还未停止");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted 取消中断的线程
            pool.shutdownNow();
            // 保留中断状态
            Thread.currentThread().interrupt();
        }
    }

    protected abstract K getKey(V v);
    protected abstract V getBean();
}
