package com.xz.pulsar.demo;

import com.xz.pulsar.demo.entity.User;
import com.xz.pulsar.utils.PulsarConntionUtil;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.MessageIdImpl;
import org.apache.pulsar.client.impl.MessageImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 消费端设置ack消费超时策略 超过ack时间后，再次消费
 */
public class Demo3 {

    private PulsarClient client ;
    private String topicName ;
    private String name ;
    @Before
    public void before(){
        name = "demo3" ;
        topicName = "persistent://pulsar/xz/"+name ;
        client = PulsarConntionUtil.getClient() ;
    }

    @Test
    public void producer(){
        Producer<User> producer = null;
        try {
            producer = client.newProducer(Schema.AVRO(User.class))
                    .topic(topicName)
                    .autoUpdatePartitions(true)
                    .producerName(name)
                    .blockIfQueueFull(true)
                    .create() ;
            for (int i = 0; i < 2 ; i++) {
                User user = new User() ;
                user.setAccount(i+"");
                user.setPassword(i+"password");
                producer.send(user) ;
            }

        } catch (PulsarClientException e) {
            e.printStackTrace();
        }finally {
            PulsarConntionUtil.releaseProduct(producer);
        }
    }

    @Test
    public void consumer(){
        Consumer<User> consumer = null;
        try {
            consumer = client.newConsumer(Schema.AVRO(User.class))
                    .topic(topicName)
                    .subscriptionName(name)
                    .subscriptionType(SubscriptionType.Shared)
                    .ackTimeout(30L, TimeUnit.SECONDS)
                    .subscribe() ;
            Long timestart = System.currentTimeMillis() ;
            while (true){
                Message<User> msg = consumer.receive();
                Long timeend = System.currentTimeMillis() ;
                System.out.println(timeend-timestart+"--"+msg.getValue().toString());
            }

        } catch (PulsarClientException e) {
            e.printStackTrace();
        }finally {
            PulsarConntionUtil.releaseConsumer(consumer);
        }
    }

    @After
    public void after(){
         if (client!=null){
             try {
                 client.close();
             } catch (PulsarClientException e) {
                 e.printStackTrace();
             }
         }
    }
}
