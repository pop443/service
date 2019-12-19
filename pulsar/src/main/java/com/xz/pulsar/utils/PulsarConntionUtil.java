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
    private static String clientUrl = "pulsar://vdapp126:6650" ;
    private static String adminUrl = "http://vdapp126:8083" ;

    public static PulsarAdmin getAdmin(){
        try {
            return PulsarAdmin.builder()
                    .serviceHttpUrl(adminUrl)
                    .build();
        } catch (PulsarClientException e) {
            e.printStackTrace();
            return null ;
        }
    }

    public static PulsarClient getClient(){
        try {
            return PulsarClient.builder()
                    .serviceUrl(clientUrl)
                    .build();
        } catch (PulsarClientException e) {
            e.printStackTrace();
            return null ;
        }
    }
    public static void release(Producer producer){
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

    public static void release(Consumer consumer){
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
    public static void release(PulsarClient client){
        if (client==null){
            return;
        }
        try {
            client.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
    public static void release(PulsarAdmin admin){
        if (admin==null){
            return;
        }
        admin.close();
    }
}
