package com.xz.ignite.basefunction.continuousquery;

import com.xz.ignite.basefunction.entity.ListenEntity;
import org.apache.ignite.lang.IgniteAsyncCallback;
import org.apache.ignite.lang.IgniteClosure;

import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListenerException;

/**
 * 是否过滤
 */
@IgniteAsyncCallback
public class CustCacheEntryEventClosure implements IgniteClosure<CacheEntryEvent<? extends String, ? extends ListenEntity>, String> {

    @Override
    public String apply(CacheEntryEvent<? extends String, ? extends ListenEntity> cacheEntryEvent) {
        StringBuilder sb = new StringBuilder() ;
        sb.append("------CustCacheEntryEventClosure-----\r\n")
                .append("type:").append(cacheEntryEvent.getEventType())
                .append("key:").append(cacheEntryEvent.getKey())
                .append("value:").append(cacheEntryEvent.getValue())
                .append("---------\r\n");
        System.out.println(sb.toString());
        return sb.toString();
    }
}
