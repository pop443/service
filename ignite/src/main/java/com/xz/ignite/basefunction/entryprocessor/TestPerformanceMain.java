package com.xz.ignite.basefunction.entryprocessor;

import com.xz.ignite.utils.CacheConfigurationUtil;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2020/1/7.
 */
public class TestPerformanceMain {
    private static final Logger LOG = LoggerFactory.getLogger(TestPerformanceMain.class);

    public static void main(String[] args) {
        LOG.debug("start");
        boolean bo = false;
        String arg = "aaaa";
        try {
            bo = args.length > 0;
            arg = args[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        Ignite ignite = IgniteUtil.getIgnite();
        try {
            CacheConfiguration<String, String> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, String.class);
            cacheConfiguration.setName("XZ");
            IgniteCache<String, String> igniteCache = ignite.cache(cacheConfiguration.getName());
            if (igniteCache == null) {
                igniteCache = ignite.createCache(cacheConfiguration);

            }
            for (int i = 0; i < 20; i++) {
                igniteCache.putIfAbsent(i + "", i + "");
            }
            IgniteCache<String, String> finalIgniteCache = igniteCache;
            for (int i = 0; i < 10; i++) {
                String finalArg = arg;
                String finalI = i + "";
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                long l1 = System.currentTimeMillis();
                                boolean bo1 = finalIgniteCache.invoke(finalI, new TestEP(), finalArg);
                                long l2 = System.currentTimeMillis();
                                System.out.println(finalI + "--result:" + (l2 - l1) + "--" + bo1);

                                Thread.sleep(1000L);

                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                thread.join();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IgniteUtil.release(ignite);
        }
    }
}
