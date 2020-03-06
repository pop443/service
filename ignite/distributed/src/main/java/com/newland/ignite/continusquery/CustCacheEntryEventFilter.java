package com.newland.ignite.continusquery;

import org.apache.ignite.lang.IgniteAsyncCallback;

import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListenerException;

/**
 * 是否过滤
 */
@IgniteAsyncCallback
public class  CustCacheEntryEventFilter implements CacheEntryEventFilter<Object,Object> {

    @Override
    public boolean evaluate(CacheEntryEvent<? extends Object, ? extends Object> cacheEntryEvent) throws CacheEntryListenerException {
        StringBuilder sb = new StringBuilder() ;
        sb.append("------CustCacheEntryEventFilter-----\r\n")
            .append("type:").append(cacheEntryEvent.getEventType())
                .append("key:").append(cacheEntryEvent.getKey())
                .append("value:").append(cacheEntryEvent.getValue())
            .append("---------\r\n");
        System.out.println(sb.toString());
        return true;
    }
}
