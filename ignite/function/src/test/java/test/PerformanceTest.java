package test;

import com.newland.boss.script.performance.EnterParam;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;

import javax.cache.Cache;
import javax.cache.CacheException;
import java.util.Iterator;

/**
 * Created by xz on 2020/9/24.
 */
public class PerformanceTest {
    public static void main(String[] args) {

        try {
            args = new String[]{"1000", "2", "1", "2", "3"};
            EnterParam enterParam = EnterParam.getEnterParam(args);
            int base3 = (enterParam.getIndex() - 1) * enterParam.getCount() * enterParam.getThreadNum() * enterParam.getLoop();
            System.out.println(base3);
            for (int u = 0; u < enterParam.getLoop(); u++) {
                int base2 = u  * enterParam.getCount() * enterParam.getThreadNum() + base3;
                System.out.println(base2);
                for (int i = 0; i < enterParam.getThreadNum(); i++) {
                    int baseKey = i * enterParam.getCount()+base2;
                    System.out.println(baseKey + "---" + (enterParam.getCount() + baseKey));
                }
                System.out.println("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
