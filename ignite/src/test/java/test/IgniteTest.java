package test;

import com.xz.ignite.basefunction.entryprocessor.TestEP;

import javax.cache.processor.EntryProcessor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2019/12/25.
 */
public class IgniteTest {
    public static void main(String[] args) {
        Map<String,String> map = new ConcurrentHashMap<>(8) ;
        map.put("9","a9") ;
        for (int i = 16; i < 32; i++) {
            int num = i+1 ;
            map.put(num+"","a"+num) ;
            System.out.println(num);
        }

        while(true){
            for (Map.Entry<String,String> entry:map.entrySet()) {
                System.out.println(entry.getKey()+"---"+entry.getValue());
                break;
            }

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
