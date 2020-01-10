package com.xz.ignite.basefunction.computer;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.binary.BinaryObjectBuilder;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.LoggerResource;

import java.util.UUID;

/**
 * Created by Administrator on 2020/1/6.
 */
public class CallableDemo2 implements IgniteCallable<String>{
    private String cacheName ;
    private UUID uuid ;

    public CallableDemo2(String cacheName, UUID uuid) {
        this.cacheName = cacheName;
        this.uuid = uuid;
    }

    @IgniteInstanceResource
    Ignite ignite;

    @LoggerResource
    private IgniteLogger log;

    @Override
    public String call() throws Exception {
        IgniteCache<UUID,BinaryObject> igniteCache = ignite.cache(cacheName).withKeepBinary() ;
        BinaryObjectBuilder binaryObjectBuilder = igniteCache.get(uuid).toBuilder() ;
        StringBuilder sb = new StringBuilder() ;
        sb.append((String)binaryObjectBuilder.getField("col1"))
                .append((String)binaryObjectBuilder.getField("col2"))
                .append((String)binaryObjectBuilder.getField("col4"))
                .append((String)binaryObjectBuilder.getField("col5")) ;
        String result = sb.toString() ;
        log.info("---------------result:"+result);
        return result;
    }
}
