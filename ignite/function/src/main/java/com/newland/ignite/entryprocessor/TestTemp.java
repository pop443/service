package com.newland.ignite.entryprocessor;

import com.newland.ignite.entryprocessor.entity.Temp;
import com.newland.ignite.entryprocessor.entity.TempConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by Administrator on 2020/1/7.
 */
public class TestTemp {
    private static final Logger LOG = LoggerFactory.getLogger(TestTemp.class);

    public static void main(String[] args) {
        LOG.debug("start");
        Ignite ignite = IgniteUtil.getIgnite();
        TempConfiguration cfg = new TempConfiguration() ;
        IgniteCache<UUID,Temp> igniteCache = cfg.getIgniteCache(ignite) ;
        igniteCache.removeAll();
        Temp temp = new Temp("1", "1", "1", "1", "1");
        UUID uuid = new UUID(1L, 1L);
        igniteCache.putIfAbsent(uuid, temp);
        IgniteCache<UUID, BinaryObject> igniteCacheBinary = igniteCache.withKeepBinary();
        try {
            boolean bo1 = igniteCacheBinary.invoke(uuid, new TempEPBinary(), "aaaaaa");
            System.out.println(bo1);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cfg.close();
            IgniteUtil.release(ignite);
        }

    }
}
