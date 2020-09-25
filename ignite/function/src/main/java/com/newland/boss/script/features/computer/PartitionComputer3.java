package com.newland.boss.script.features.computer;

import com.newland.ignite.label.entity.BossAffinity;
import com.newland.ignite.label.entity.BossAffinityConfiguration;
import com.newland.ignite.label.entity.BossAffinityKey;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.*;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.binary.BinaryObjectBuilder;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.binary.builder.BinaryObjectBuilderImpl;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.lang.IgniteClosure;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.LoggerResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * 超时计算任务
 * Created by xz on 2020/8/27.
 */
public class PartitionComputer3 {
    private Ignite ignite;
    private String name;

    @Before
    public void before() {
        ignite = IgniteUtil.getIgnite();
    }

    @Test
    public void test() {
        Random random = new Random();
        IgniteCompute compute = ignite.compute();
        int count = 10;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(count, count,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        BlockingQueue<Future<Map<String, String>>> queue = new LinkedBlockingDeque<>(count);
        //实例化CompletionService
        CompletionService<Map<String, String>> completionService = new ExecutorCompletionService<>(threadPoolExecutor, queue);
        for (int i = 0; i < count; i++) {
            int sleepTime = random.nextInt(10);
            Map<String, String> inMap = new HashMap<>();
            inMap.put("index", i + "");
            inMap.put("sleepTime", sleepTime + "000");
            System.out.println("计算任务"+i+"沉睡时间为"+sleepTime);
            CustIgniteCalled ic = new CustIgniteCalled(inMap);
            Work work = new Work(compute, ic, 5L, TimeUnit.SECONDS);
            completionService.submit(work);
        }
        try {
            for (int i = 0; i < count; i++) {
                Future<Map<String, String>> future = completionService.take();
                Map<String, String> map = future.get();
                String retIndex = map.get("index");
                if (map.containsKey("host")) {
                    System.out.println("计算任务" + retIndex + "已经计算ok了 host:" + map.get("host"));
                } else {
                    System.out.println("计算任务" + retIndex + "已经超时了");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            gracefulShutdown(threadPoolExecutor, 60, 2, TimeUnit.MINUTES);
        }
    }

    public static void gracefulShutdown(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout,
                                        TimeUnit timeUnit) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
                    System.err.println("Pool did not terminated");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    @After
    public void after() {
        IgniteUtil.release(ignite);
    }

    class CustIgniteCalled implements IgniteCallable<Map<String, String>> {
        private Map<String, String> inMap;

        public CustIgniteCalled(Map<String, String> inMap) {
            this.inMap = inMap;
        }

        public Map<String, String> getInMap() {
            return inMap;
        }

        @IgniteInstanceResource
        private Ignite ignite;

        @LoggerResource
        private IgniteLogger log;

        @Override
        public Map<String, String> call() throws Exception {
            String host = ignite.cluster().localNode().attribute("HOSTNAME");
            Integer integer = Integer.parseInt(inMap.get("index"));
            Long along = Long.parseLong(inMap.get("sleepTime"));
            Thread.sleep(along);
            inMap.put("host", host);
            return inMap;
        }
    }

    class Work implements Callable<Map<String, String>> {
        private IgniteCompute compute;
        private CustIgniteCalled custIgniteCalled;
        private long timeOut;
        private TimeUnit timeUnit;

        public Work(IgniteCompute compute, CustIgniteCalled custIgniteCalled, long timeOut, TimeUnit timeUnit) {
            this.compute = compute;
            this.custIgniteCalled = custIgniteCalled;
            this.timeOut = timeOut;
            this.timeUnit = timeUnit;
        }

        @Override
        public Map<String, String> call() throws Exception {
            IgniteFuture<Map<String, String>> igniteFuture = compute.callAsync(custIgniteCalled);
            try {
                return igniteFuture.get(timeOut, timeUnit);
            } catch (Exception e) {
                System.out.println("1111");
            }
            return custIgniteCalled.getInMap();
        }
    }
}
