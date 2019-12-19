package com.xz.pulsar.demo.function;

import com.xz.pulsar.demo.entity.User;
import com.xz.pulsar.utils.PulsarConntionUtil;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.BatchMessageIdImpl;
import org.apache.pulsar.client.impl.schema.AvroSchema;
import org.apache.pulsar.client.impl.schema.JSONSchema;

/**
 * Created by Administrator on 2019/12/18.
 */
public class RandomFunctionTest {
    /**
     * 已经创建  persistent://pulsar/xz/function topic
     * @param args
     */
    public static void main(String[] args) {
        PulsarClient client = PulsarConntionUtil.getClient() ;
        Producer<User> producer = null ;
        try {
            producer = client.newProducer(JSONSchema.of(User.class)).topic("persistent://pulsar/xz/function")
                    .producerName("xzFunction").create() ;
            for (int i = 0; i < 10; i++) {
                User user = new User() ;
                user.setAccount("account"+i);
                user.setPassword("password"+i);
                MessageId messageId = producer.send(user) ;
                BatchMessageIdImpl batchMessageId = (BatchMessageIdImpl)messageId ;
                System.out.println(batchMessageId.getPartitionIndex());
            }
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }finally {
            PulsarConntionUtil.release(producer);
            PulsarConntionUtil.release(client);
        }


    }
}
