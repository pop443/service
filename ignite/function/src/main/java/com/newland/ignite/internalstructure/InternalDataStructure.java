package com.newland.ignite.internalstructure;

import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheEntry;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.internal.GridKernalContext;
import org.apache.ignite.internal.IgniteEx;
import org.apache.ignite.internal.processors.cache.GridCacheContext;
import org.apache.ignite.internal.processors.cache.GridCacheEntryEx;
import org.apache.ignite.internal.processors.cache.GridCacheSharedContext;
import org.apache.ignite.internal.processors.query.QueryUtils;

import java.util.Collection;

/**
 * Created by xz on 2020/7/2.
 */
public class InternalDataStructure {

    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite();
        try {
            IgniteCache igniteCache = ignite.cache("EXPIRY") ;
            CacheConfiguration configuration = (CacheConfiguration) igniteCache.getConfiguration(CacheConfiguration.class);
            Collection<QueryEntity> collection = configuration.getQueryEntities() ;
            if (collection==null || collection.size()==0){
                throw new Exception("cache is not support") ;
            }
            QueryEntity queryEntity = collection.iterator().next() ;
            String valueType = queryEntity.findValueType() ;
            String tableName = QueryUtils.typeName(valueType) ;
            System.out.println(tableName);
            CacheEntry cacheEntry = igniteCache.getEntry("2") ;
            System.out.println(cacheEntry.version());
            String key = "2";
            Expiry expiry = new Expiry(key, key, key, new Automation(key, 2, key));
            igniteCache.put(key, expiry);
            cacheEntry = igniteCache.getEntry("2") ;
            System.out.println(cacheEntry.version());
            IgniteEx igniteEx = (IgniteEx)ignite ;
            GridKernalContext ctx = igniteEx.context();
            GridCacheSharedContext cctx = ctx.cache().context();
            Collection<GridCacheContext> cacheContexts = cctx.cacheContexts() ;
            for (GridCacheContext gridCacheContext:cacheContexts) {
                System.out.println(gridCacheContext.name()+"--"+gridCacheContext.expiry());
            }

            GridCacheContext gridCacheContext = igniteEx.cachex("EXPIRY").context() ;
            GridCacheEntryEx e1 = gridCacheContext.cache().peekEx("3") ;
            long ttl = e1.ttl() ;
            long expireTime = e1.expireTime() ;
            System.out.println(ttl+"--"+expireTime);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IgniteUtil.release(ignite);
        }
    }
}
