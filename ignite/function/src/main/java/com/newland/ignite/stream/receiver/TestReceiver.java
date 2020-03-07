package com.newland.ignite.stream.receiver;

import com.newland.ignite.entryprocessor.entity.Temp;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.stream.StreamReceiver;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * 流处理接收事件 过滤 是否put数据
 */
public class TestReceiver implements StreamReceiver<UUID, Temp>{
    @Override
    public void receive(IgniteCache<UUID, Temp> cache, Collection<Map.Entry<UUID, Temp>> entries) throws IgniteException {
        System.out.println(cache.getName());
        for (Map.Entry<UUID, Temp> entry:entries) {
            System.out.println(entry.getKey()+"--"+entry.getValue());
            cache.put(entry.getKey(),entry.getValue());
        }
    }
}
