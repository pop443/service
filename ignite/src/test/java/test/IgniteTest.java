package test;

import com.xz.ignite.basefunction.entryprocessor.TestEP;

import javax.cache.processor.EntryProcessor;

/**
 * Created by Administrator on 2019/12/25.
 */
public class IgniteTest {
    public static void main(String[] args) {
        Class cz = TestEP.class ;
        System.out.println(cz.getName().startsWith("org.apache.ignite"));
        System.out.println(EntryProcessor.class.isAssignableFrom(cz));

    }
}
