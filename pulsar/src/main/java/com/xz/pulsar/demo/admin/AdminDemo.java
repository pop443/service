package com.xz.pulsar.demo.admin;

import com.xz.pulsar.utils.PulsarConntionUtil;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2019/12/13.
 */
public class AdminDemo {
    private static final Logger LOG = LoggerFactory.getLogger(AdminDemo.class);

    private PulsarAdmin admin ;
    @Before
    public void before(){
        admin = PulsarConntionUtil.getAdmin() ;
    }
    @Test
    public void healthcheck(){
        LOG.info("--------- healthcheck ---------");
        try {
            admin.brokers().healthcheck();
        } catch (PulsarAdminException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createTopic(){
        LOG.info("--------- createTopic ---------");
        try {
            admin.topics().createPartitionedTopic("pulsar/xz/function",3);
        } catch (PulsarAdminException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void listTopic(){
        LOG.info("--------- listTopic ---------");
        try {
            List<String> list = admin.topics().getList("pulsar/xz");
            list.forEach(System.out::println);
            System.out.println("-----------------------------");
            list = admin.topics().getPartitionedTopicList("pulsar/xz");
            list.forEach(System.out::println);
        } catch (PulsarAdminException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void functionList(){
        LOG.info("--------- functionList ---------");
        try {
            List<String> list = admin.functions().getFunctions("pulsar","xz") ;
            list.forEach(System.out::println);
        } catch (PulsarAdminException e) {
            e.printStackTrace();
        }
    }



    @After
    public void after(){
         if (admin!=null){
             admin.close();
         }
    }
}
