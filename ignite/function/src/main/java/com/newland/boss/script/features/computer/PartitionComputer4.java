package com.newland.boss.script.features.computer;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.LoggerResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.Cache;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 读取某个节点上 包含的所有的key
 * Created by xz on 2020/8/27.
 */
public class PartitionComputer4 {
    private Ignite ignite;
    private String cacheName;

    @Before
    public void before() {
        cacheName = "EXPIRY" ;
        ignite = IgniteUtil.getIgnite();
    }

    @Test
    public void test() {
        Collection<ClusterNode> collection = ignite.cluster().forServers().nodes() ;
        int count = collection.size();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(count, count,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        BlockingQueue<Future<Map<String, String>>> queue = new LinkedBlockingDeque<>(count);
        //实例化CompletionService
        CompletionService<Map<String, String>> completionService = new ExecutorCompletionService<>(threadPoolExecutor, queue);
        int index = 0 ;
        for (ClusterNode clusterNode:collection) {
            Map<String, String> inMap = new HashMap<>();
            inMap.put("index", index++ + "");
            inMap.put("cacheName", cacheName);
            CustIgniteCalled ic = new CustIgniteCalled(inMap);
            Work work = new Work(ignite.compute(ignite.cluster().forNode(clusterNode)), ic, 5L, TimeUnit.SECONDS);
            completionService.submit(work);
        }

        try {
            int size = 0 ;
            for (int i = 0; i < count; i++) {
                Future<Map<String, String>> future = completionService.take();
                Map<String, String> map = future.get();
                String retIndex = map.get("index");
                String host = map.get("host");
                String keyResult = map.get("keyResult");
                size = size+keyResult.split(",").length ;
                System.out.println(retIndex+"-----------"+host+"-----------"+keyResult);
            }
            System.out.println("size"+size);
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
            inMap.put("host", host);
            String cacheName = inMap.get("cacheName");

            IgniteCache igniteCache = ignite.cache(cacheName).withKeepBinary() ;
            StringBuilder sb = new StringBuilder() ;
            ScanQuery<String, String> scanQuery = new ScanQuery<String, String>()
                    .setLocal(true);
            Affinity affinity = ignite.affinity(cacheName) ;
            int[] partitions = affinity.primaryPartitions(ignite.cluster().localNode()) ;
            for (int partition: partitions) {
                System.out.println("partition"+partition);
                scanQuery.setPartition(partition) ;
                QueryCursor cursor = igniteCache.query(scanQuery) ;
                for (Object p:cursor) {
                    String key = (String) ((Cache.Entry)p).getKey();
                    sb.append(",").append(key);
                }
                cursor.close();
            }

            inMap.put("keyResult",sb.substring(1)) ;

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
