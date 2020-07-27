package test;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.structure.ChangeData;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
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
import java.sql.Timestamp;
import java.util.*;

/**
 *
 * Created by Administrator on 2019/12/25.
 */
public class IgniteTest {
    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite();
        try {
            IgniteCache igniteCache = ignite.cache("COMPANY");
            igniteCache.put("companyId1", new PartitionCustObj("2", "2"));
            Object o1 = igniteCache.get("companyId0");
            System.out.println(o1);
            System.out.println(igniteCache.sizeLong(CachePeekMode.PRIMARY));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IgniteUtil.release(ignite);
        }
    }
}
