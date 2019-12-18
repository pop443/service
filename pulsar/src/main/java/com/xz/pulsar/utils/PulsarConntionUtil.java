package com.xz.pulsar.utils;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

/**
 * Created by Administrator on 2019/12/13.
 */
public class PulsarConntionUtil {
    private static String url = "pulsar://vdapp126:6650" ;

    public static PulsarAdmin getAdmin(){
        try {
            return PulsarAdmin.builder()
                    .serviceHttpUrl(url)
                    .build();
        } catch (PulsarClientException e) {
            e.printStackTrace();
            return null ;
        }
    }

    public static PulsarClient getClient(){
        try {
            return PulsarClient.builder()
                    .serviceUrl(url)
                    .build();
        } catch (PulsarClientException e) {
            e.printStackTrace();
            return null ;
        }
    }
    public static void releaseProduct(Producer producer){
        if (producer==null){
            return;
        }
        if (producer.isConnected()){
            try {
                producer.close();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        }
    }

    public static void releaseConsumer(Consumer consumer){
        if (consumer==null){
            return;
        }
        if (consumer.isConnected()){
            try {
                consumer.close();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        }
    }
}
