package test;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.affinity.Affinity;

/**
 * Created by Administrator on 2019/12/25.
 */
public class IgniteTest {
    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite();
        ignite.cacheNames().forEach(System.out::println);
        IgniteCache<Affinity<Integer>,BinaryObject> cache = ignite.cache("SQL_PUBLIC_CAR").withKeepBinary() ;
        ignite.close();
    }
}
