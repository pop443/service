package ignite.computer;

import com.xz.ignite.basefunction.computer.ApplyDemo2;
import com.xz.ignite.basefunction.computer.CallableDemo1;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.lang.IgniteCallable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Administrator on 2020/1/6.
 */
public class ComputerTest {

    private Ignite ignite ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void computer(){
        Collection<IgniteCallable<Integer>> calls = new ArrayList<>();

        // Iterate through all the words in the sentence and create Callable jobs.
        for (final String word : "Count characters using callable".split(" ")) {
            calls.add(new IgniteCallable<Integer>() {
                @Override public Integer call() throws Exception {
                    return word.length();
                }
            });
        }

        // Execute collection of Callables on the grid.
        Collection<Integer> res = ignite.compute().call(calls);

        int sum = 0;

        // Add up individual word lengths received from remote nodes.
        for (int len : res){
            sum += len;
        }

        System.out.println(">>> Total number of characters in the phrase is '" + sum + "'.");
    }

    @Test
    public void apply(){
        IgniteCompute igniteCompute = ignite.compute() ;
        Integer integer = igniteCompute.apply(new ApplyDemo2(),"123123134");
        System.out.println("result----------"+integer);
    }

    @Test
    public void tableComputer(){
        IgniteCompute igniteCompute = ignite.compute() ;
        String cacheName = "TEMP" ;
        UUID uuid = new UUID(1,1) ;
        String s = igniteCompute.affinityCall(cacheName, uuid, new CallableDemo1(cacheName,uuid));
        System.out.println("result----------"+s);
    }

    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
