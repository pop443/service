package com.xz.pulsar.demo;

import com.xz.pulsar.utils.PulsarConntionUtil;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.MessageIdImpl;
import org.apache.pulsar.client.impl.MessageImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/12/13.
 */
public class Demo2 {

    private PulsarClient client ;
    private String topicName ;
    private String name ;
    @Before
    public void before(){
        name = "demo2" ;
        topicName = "persistent://pulsar/xz/"+name ;
        client = PulsarConntionUtil.getClient() ;
    }

    @Test
    public void producer(){
        Producer<String> producer = null;
        try {
            producer = client.newProducer(Schema.STRING)
                    .topic(topicName)
                    .autoUpdatePartitions(true)
                    .producerName(name)
                    .blockIfQueueFull(true)
                    .create() ;
            producer.newMessage().key("key-1").value("message-1-1").property("hello","you").send();
            producer.newMessage().key("key-1").value("message-1-2").property("hello","you").send();
            producer.newMessage().key("key-1").value("message-1-3").property("hello","you").send();
            producer.newMessage().key("key-2").value("message-2-1").property("hello","you").send();
            producer.newMessage().key("key-2").value("message-2-2").property("hello","you").send();
            producer.newMessage().key("key-2").value("message-2-3").property("hello","you").send();
            producer.newMessage().key("key-3").value("message-3-1").property("hello","you").send();
            producer.newMessage().key("key-3").value("message-3-2").property("hello","you").send();
            producer.newMessage().key("key-4").value("message-4-1").property("hello","you").send();
            producer.newMessage().key("key-4").value("message-4-2").property("hello","you").send();

        } catch (PulsarClientException e) {
            e.printStackTrace();
        }finally {
            PulsarConntionUtil.releaseProduct(producer);
        }
    }

    @Test
    public void consumer(){
        Consumer<String> consumer = null;
        try {
            consumer = client.newConsumer(Schema.STRING)
                    .topic(topicName)
                    .subscriptionName(name)
                    .subscriptionType(SubscriptionType.Exclusive)
                    .subscribe() ;
            while (true){
                MessageImpl<String> msg = (MessageImpl<String>)consumer.receive();
                MessageIdImpl messageId = (MessageIdImpl)msg.getMessageId() ;
                System.out.println(messageId.getPartitionIndex()+"---"+msg.getKey()+"--"+msg.getValue()+"--"+msg.getProperty("hello"));
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
