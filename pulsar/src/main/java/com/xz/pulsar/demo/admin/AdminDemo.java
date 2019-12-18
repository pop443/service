package com.xz.pulsar.demo.admin;

import com.xz.pulsar.utils.PulsarConntionUtil;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.BatchMessageIdImpl;
import org.apache.pulsar.client.impl.MessageIdImpl;
import org.apache.pulsar.client.impl.TopicMessageIdImpl;
import org.apache.pulsar.client.impl.TopicMessageImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2019/12/13.
 */
public class AdminDemo {

    private PulsarAdmin admin ;
    @Before
    public void before(){
        admin = PulsarConntionUtil.getAdmin() ;
    }

    @Test
    public void producer(){
    }


    @After
    public void after(){
         if (admin!=null){
             admin.close();
         }
    }
}
