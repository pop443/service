package com.newland.boss.script.performance.loaddata;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.LoggerResource;

/**
 * Created by xz on 2020/5/1.
 */
public class LoadData implements LifecycleBean {
    @IgniteInstanceResource
    private Ignite ignite;
    @LoggerResource
    private IgniteLogger logger;
    private String cacheName = "PARTITIONCUSTOBJ";

    @Override
    public void onLifecycleEvent(LifecycleEventType evt) throws IgniteException {
        if (evt == LifecycleEventType.AFTER_NODE_START&&ignite.cluster().active()) {
            loaddata();
        }
    }

    private void loaddata() {
        IgniteCache<String, BinaryObject> igniteCache = ignite.cache(cacheName);
        if (igniteCache == null){
            logger.info("无缓存--不用加载");
            return;
        }
        logger.info("开始加载"+cacheName);
        Affinity aff = ignite.affinity(cacheName) ;

        int partitionSize = aff.partitions() ;
        logger.info("加载"+partitionSize+"个分区");
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < partitionSize; i++) {
            igniteCache.preloadPartition(i);
        }
        long l2 = System.currentTimeMillis() ;
        logger.info("加载耗时："+(l2-l1));
    }
}
