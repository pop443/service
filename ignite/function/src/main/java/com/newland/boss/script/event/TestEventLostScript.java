package com.newland.boss.script.event;

import com.newland.boss.entity.event.Event;
import com.newland.boss.entity.event.EventConfiguration;
import com.newland.boss.script.BaseScript;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.binary.BinaryObjectBuilder;
import org.apache.ignite.binary.BinaryObjectException;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.util.HashMap;
import java.util.Map;

/**
 * 3.8.1 3.8.2 监听系统事件和缓存事件
 */
public class TestEventLostScript extends BaseScript<String,Event> {
    public TestEventLostScript() {
        super(new EventConfiguration());
    }

    @Override
    public void work() {
        igniteCache.lostPartitions().forEach(System.out::println);
    }

    public static void main(String[] args) {
        TestEventLostScript scirpt = new TestEventLostScript() ;
        scirpt.start();
    }
}
