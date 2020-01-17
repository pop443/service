package com.xz.ignite.basefunction.stream.receiver;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.stream.StreamReceiver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 流处理接收事件 过滤 是否put数据
 */
public class TestReceiver implements StreamReceiver<String,String>{
    @Override
    public void receive(IgniteCache<String, String> cache, Collection<Map.Entry<String, String>> entries) throws IgniteException {
        System.out.println(cache.getName());
        for (Map.Entry<String, String> entry:entries) {
            System.out.println(entry.getKey()+"--"+entry.getValue());
            cache.put(entry.getKey(),entry.getValue());
        }
    }
}
