package com.xz.ignite.basefunction.entryprocessor;

import com.xz.ignite.basefunction.entity.Temp;
import com.xz.ignite.utils.CacheConfigurationUtil;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by Administrator on 2020/1/7.
 */
public class TempMain {
    private static final Logger LOG = LoggerFactory.getLogger(TempMain.class);

    public static void main(String[] args) {
        LOG.debug("start");
        Ignite ignite = IgniteUtil.getIgnite();

        try {
            CacheConfiguration<UUID, Temp> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(UUID.class, Temp.class);
            IgniteCache<UUID, Temp> igniteCache = ignite.cache(cacheConfiguration.getName()) ;
            UUID uuid = new UUID(1L, 1L);
            if (igniteCache==null){
                igniteCache = ignite.createCache(cacheConfiguration) ;
                Temp temp = new Temp("1", "1", "1", "1", "1");
                igniteCache.putIfAbsent(uuid, temp);
            }

            IgniteCache<UUID, BinaryObject> igniteCacheBinary = igniteCache.withKeepBinary();
            boolean bo1 = igniteCacheBinary.invoke(uuid, new TempEPBinary(), "aaaaaa");
            System.out.println(bo1);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IgniteUtil.release(ignite);
        }


    }
}
