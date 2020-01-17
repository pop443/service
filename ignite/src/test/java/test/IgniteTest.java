package test;

import com.xz.ignite.basefunction.entryprocessor.TestEP;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.Event;
import org.apache.ignite.events.EventType;
import org.apache.ignite.internal.util.IgniteUtils;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import javax.cache.processor.EntryProcessor;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2019/12/25.
 */
public class IgniteTest {
    public static void main(String[] args) {
        System.out.println(IgniteUtils.class.getName().substring(0, IgniteUtils.class.getName().
                indexOf('.', IgniteUtils.class.getName().indexOf('.') + 1)));
        System.out.println(Integer.toHexString(Ignite.class.getClassLoader().hashCode()));

    }
}
