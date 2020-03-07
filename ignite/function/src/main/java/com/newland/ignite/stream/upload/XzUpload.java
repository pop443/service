package com.newland.ignite.stream.upload;

import com.newland.ignite.entryprocessor.entity.Temp;
import com.newland.ignite.entryprocessor.entity.TempConfiguration;
import com.newland.ignite.stream.receiver.TestReceiver;
import com.newland.ignite.utils.CustCacheConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.IgniteDataStreamerTimeoutException;
import org.apache.ignite.configuration.CacheConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 流数据无法被监听事件捕获
 */
public class XzUpload {
    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite() ;
        TempConfiguration cfg = new TempConfiguration() ;
        IgniteDataStreamer<UUID, Temp> stmr = cfg.getDataStreamer(ignite);
        try {
            stmr.receiver(new TestReceiver());
            stmr.allowOverwrite(true);
            Map<UUID, Temp> map = new HashMap<>() ;
            int i = 150 ;
            while (i<200){
                String value = i+"" ;
                map.put(new UUID(i,i),new Temp(value,value,value,value,value)) ;
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
            cfg.close();
            IgniteUtil.release(ignite);
        }



    }
}
