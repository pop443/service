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
public class TestEventScript extends BaseScript<String,Event> {
    public TestEventScript() {
        super(new EventConfiguration());
    }

    @Override
    public void work() {
        int index = 10 ;

        Map<String,Event> map = new HashMap<>() ;
        for (int i = 0; i < index; i++) {
            String key = i+"" ;
            map.put(key,new Event(key,key,i));
        }
        igniteCache.putAll(map);
        System.out.println(" >>> 插入"+index+"条事件缓存数据");
        igniteCache.remove("1");
        System.out.println(" >>> 删除1条事件数据缓存数据");
        igniteCache.removeAll();


        Map<String,Event> idsMap = new HashMap<>() ;
        for (int i = 10; i < index+10; i++) {
            String key = i+"" ;
            idsMap.put(key,new Event(key,key,i));
        }
        IgniteDataStreamer<String,Event> ids = getIgniteDataStreamer();
        ids.keepBinary(true);
        ids.addData(idsMap) ;
        ids.flush();
        ids.close();

        IgniteCache<String,BinaryObject> igniteCacheBinary = (igniteCache).withKeepBinary() ;

        boolean bo = igniteCacheBinary.invoke("11", new EntryProcessor<String, BinaryObject, Boolean>() {
            @Override
            public Boolean process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
                boolean bo = false ;
                try {
                    String newName = (String)objects[0] ;
                    BinaryObject binaryObject = mutableEntry.getValue() ;
                    BinaryObjectBuilder builder = binaryObject.toBuilder() ;
                    String oldName = builder.getField("name") ;
                    builder.setField("name",oldName+newName) ;
                    mutableEntry.setValue(builder.build());
                    bo = true ;
                } catch (BinaryObjectException e) {
                    e.printStackTrace();
                }
                return bo;
            }
        },"hhehheh");
        System.out.println(bo);
        igniteCache.removeAll();
    }

    public static void main(String[] args) {
        TestEventScript scirpt = new TestEventScript() ;
        scirpt.start();
    }
}
