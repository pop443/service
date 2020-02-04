package com.xz.netty.demo.gcust.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 断线重连线程 一直尝试
 */
public class ReConnectThread implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ReConnectThread.class);

    private MainCustClient mainCustClient ;

    public ReConnectThread(MainCustClient mainCustClient) {
        this.mainCustClient = mainCustClient;
    }

    @Override
    public void run() {
            reconnect();
    }

    private void reconnect(){
        try {
            TimeUnit.SECONDS.sleep(5);
            logger.info("-------开始重连-------");
            mainCustClient.start();
        } catch (Exception e) {
            String threadName = Thread.currentThread().getName() ;
            logger.error(threadName+"--:"+e.getMessage());
        }
    }
}
