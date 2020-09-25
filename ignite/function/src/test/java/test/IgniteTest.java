package test;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.structure.ChangeData;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheEntry;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.internal.GridKernalContext;
import org.apache.ignite.internal.IgniteEx;
import org.apache.ignite.internal.processors.cache.GridCacheContext;
import org.apache.ignite.internal.processors.cache.GridCacheEntryEx;
import org.apache.ignite.internal.processors.cache.GridCacheSharedContext;
import org.apache.ignite.internal.processors.cache.GridCacheUtils;
import org.apache.ignite.internal.processors.query.QueryUtils;
import org.apache.ignite.internal.util.IgniteUtils;

import javax.cache.Cache;
import javax.cache.CacheException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2019/12/25.
 */
public class IgniteTest {
    public static void main(String[] args) {
        Ignite ignite = null ;
        try {
            ignite = IgniteUtil.getIgnite() ;
            IgniteCache<String,BinaryObject> igniteCache = ignite.cache("user").withKeepBinary();
            Iterator<Cache.Entry<String,BinaryObject>> it = igniteCache.iterator() ;
            while (it.hasNext()){
                Cache.Entry<String,BinaryObject> entry = it.next() ;
                System.out.println(entry.getKey());
            }

            BinaryObject binaryObject = igniteCache.get("'2L'") ;
            System.out.println(binaryObject==null);
            BinaryObject binaryObject2 = igniteCache.get("2L") ;
            System.out.println(binaryObject2==null);
        } catch (CacheException e) {
            e.printStackTrace();
        }finally {
            IgniteUtil.release(ignite);
        }
    }
}
