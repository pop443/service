package test;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.IgniteKernal;
import org.apache.ignite.internal.processors.cache.distributed.dht.GridDhtCacheAdapter;

import java.util.Collection;

/**
 * Created by Administrator on 2019/12/25.
 */
public class IgniteTest {
    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite();
        IgniteCluster cluster = ignite.cluster();

        Collection<ClusterNode> clusterNodes = cluster.nodes() ;


        IgniteUtil.release(ignite);
    }
}
