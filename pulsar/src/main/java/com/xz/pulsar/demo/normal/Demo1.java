package com.xz.pulsar.demo.normal;

import com.xz.pulsar.utils.PulsarConntionUtil;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * producer consumer
 */
public class Demo1 {

    private PulsarClient client ;
    private String topicName ;
    private String name ;
    @Before
    public void before(){
        name = "demo1" ;
        topicName = "persistent://pulsar/xz/"+name ;
        client = PulsarConntionUtil.getClient() ;
    }

    @Test
    public void producer(){
        Producer<byte[]> producer = null;
        try {
            producer = client.newProducer()
                    .topic(topicName)
                    .producerName(name)
                    .blockIfQueueFull(true)
                    .create() ;
            producer.newMessage().key("key-1").value("message-1".getBytes()).send();
            producer.newMessage().key("key-2").value("message-2".getBytes()).send();
            producer.newMessage().key("key-3").value("message-3".getBytes()).send();
            producer.newMessage().key("key-4").value("message-4".getBytes()).send();

        } catch (PulsarClientException e) {
            e.printStackTrace();
        }finally {
            PulsarConntionUtil.release(producer);
        }
    }

    @Test
    public void consumer(){
        Consumer<byte[]> consumer = null;
        try {
            consumer = client.newConsumer()
                    .topic(topicName)
                    .subscriptionName(name)
                    .subscriptionType(SubscriptionType.Shared)
                    .subscribe() ;
            while (true){
                TopicMessageImpl<byte[]> msg = (TopicMessageImpl<byte[]>)consumer.receive();
                TopicMessageIdImpl topicMessageId = (TopicMessageIdImpl)msg.getMessageId() ;
                System.out.println(topicMessageId.getTopicPartitionName()+"---"+msg.getKey()+"--"+new String(msg.getValue()));
                //consumer.acknowledge(msg);
            }

        } catch (PulsarClientException e) {
            e.printStackTrace();
        }finally {
            PulsarConntionUtil.release(consumer);
        }
    }

    @Test
    public void producerAsync(){
        CountDownLatch downLatch = new CountDownLatch(1) ;
        Producer<byte[]> producer = null;
        try {
            producer = client.newProducer()
                    .topic(topicName)
                    .producerName(name)
                    .blockIfQueueFull(true)
                    .create() ;
            producer.newMessage().key("key-1").value("message-1-1".getBytes()).sendAsync().thenAccept(messageId -> {
                BatchMessageIdImpl batchMessageId = (BatchMessageIdImpl)messageId ;
                MessageIdImpl messageId1 = batchMessageId.prevBatchMessageId() ;
                System.out.println(batchMessageId.getPartitionIndex()+"--"+messageId1.getLedgerId()+"--"+messageId1.getEntryId());
                downLatch.countDown();
            });
            downLatch.await();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            PulsarConntionUtil.release(producer);
        }
    }




    @Test
    public void consumerAsync(){
        Consumer<byte[]> consumer = null;
        try {
            consumer = client.newConsumer()
                    .topic(topicName)
                    .subscriptionName(name)
                    .subscriptionType(SubscriptionType.Exclusive)
                    .subscribe() ;
            while (true){
                Consumer<byte[]> finalConsumer = consumer;
                consumer.receiveAsync().thenAccept((Message<byte[]> message) -> {
                    TopicMessageImpl<byte[]> topicMessage = (TopicMessageImpl<byte[]>)message ;
                    System.out.println(topicMessage.getTopicPartitionName()+"--"+topicMessage.getKey()+"--"+new String(message.getValue()));
                    try {
                        finalConsumer.acknowledge(message);
                    } catch (PulsarClientException e) {
                        e.printStackTrace();
                    }
                });


            }
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }finally {
            PulsarConntionUtil.release(consumer);
        }
    }

    @After
    public void after(){
        PulsarConntionUtil.release(client);
    }
}
