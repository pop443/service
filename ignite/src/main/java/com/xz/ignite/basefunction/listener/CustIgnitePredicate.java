package com.xz.ignite.basefunction.listener;

import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.lang.IgnitePredicate;

import java.util.UUID;

/**
 * 添加监听事件 chache的put事件 流处理无法被捕获
 */
public class CustIgnitePredicate {
    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite() ;
        ignite.events(ignite.cluster().forCacheNodes("XZ")).remoteListen(new IgniteBiPredicate<UUID, CacheEvent>() {
            @Override
            public boolean apply(UUID uuid, CacheEvent event) {
                StringBuilder sb = new StringBuilder();
                String key = event.key();
                sb.append("\r\nkey:").append(key);
                sb.append("\r\nuuid:").append(uuid.toString());
                if (event.hasNewValue()) {
                    String newValue = (String) event.newValue();
                    sb.append(";newValue:").append(newValue);
                }
                if (event.hasOldValue()) {
                    String oldValue = (String) event.oldValue();
                    sb.append(";oldValue:").append(oldValue);
                }
                System.out.println(sb.toString());
                return false;
            }
        }, new IgnitePredicate<CacheEvent>() {
            @Override
            public boolean apply(CacheEvent event) {
                StringBuilder sb = new StringBuilder();
                String key = event.key();
                sb.append("\r\nkey:").append(key);
                if (event.hasNewValue()) {
                    String newValue = (String) event.newValue();
                    sb.append(";newValue:").append(newValue);
                }
                if (event.hasOldValue()) {
                    String oldValue = (String) event.oldValue();
                    sb.append(";oldValue:").append(oldValue);
                }
                System.out.println(sb.toString());

                return false;
            }
        }, EventType.EVT_CACHE_OBJECT_PUT);

    }
}
