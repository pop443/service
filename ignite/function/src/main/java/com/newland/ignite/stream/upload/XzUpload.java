package com.newland.ignite.stream.upload;

import com.newland.ignite.stream.receiver.TestReceiver;
import com.newland.ignite.utils.CacheConfigurationUtil;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.IgniteDataStreamerTimeoutException;
import org.apache.ignite.configuration.CacheConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * 流数据无法被监听事件捕获
 */
public class XzUpload {
    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite() ;
        IgniteDataStreamer<String, String> stmr = null;
        try {
            CacheConfiguration<String,String> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class,String.class) ;
            cacheConfiguration.setName("XZ") ;
            cacheConfiguration.setStatisticsEnabled(true) ;
            stmr = ignite.dataStreamer(cacheConfiguration.getName());
            stmr.receiver(new TestReceiver());
            stmr.allowOverwrite(true);
            Map<String,String> map = new HashMap<>() ;
            int i = 150 ;
            while (true){
                map.put(i+"",i+"") ;
                stmr.addData(map) ;
                stmr.flush();
                System.out.println(i);
                Thread.sleep(1000L);
                i++ ;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IgniteDataStreamerTimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stmr.close();
            IgniteUtil.release(ignite);
        }



    }
}
