package com.newland.ignite.metrics;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/1/16.
 */
public class MBeanConnection {
    private String baseUrl;
    private long timeout ;
    private Map<String, Object> map;

    public MBeanConnection(String baseUrl,long timeout) {
        this.baseUrl = baseUrl;
        this.timeout = timeout ;
        map = new HashMap<>();
        map.put(JMXConnector.CREDENTIALS, new String[]{"root", "root123."});
        map.put("jmx.remote.x.request.waiting.timeout", "3000");
        map.put("jmx.remote.x.notification.fetch.timeout", "3000");
        map.put("sun.rmi.transport.connectionTimeout", "3000");
        map.put("sun.rmi.transport.tcp.handshakeTimeout", "3000");
        map.put("sun.rmi.transport.tcp.responseTimeout", "3000");
    }
    public void start() {
        try {
            JMXServiceURL url = new JMXServiceURL(baseUrl);
            JMXConnector jmxc = JMXConnectorFactory.connect(url, map);
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
            allwaysPrint(mbsc);
            // 把所有Domain都打印出来
            //printAttributes(mbsc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void allwaysPrint(MBeanServerConnection mbsc) {
        while (true){
            ObjectName objectName = null ;
            AttributeList attributeList = null ;
            try {
                objectName = new ObjectName("java.nio:type=BufferPool,name=direct");
                attributeList = mbsc.getAttributes(objectName, new String[]{"Count", "MemoryUsed", "TotalCapacity"});
                printValue(attributeList.asList());

                objectName = new ObjectName("org.apache:group=\"Thread Pools\",name=StripedExecutor");
                attributeList = mbsc.getAttributes(objectName, new String[]{"TotalQueueSize", "TotalCompletedTasksCount", "ActiveCount"});
                printValue(attributeList.asList());

            } catch (MalformedObjectNameException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void printValue(List<Attribute> list){
        for (Attribute attribute : list) {
            Object o = attribute.getValue();
            String name = attribute.getName();
            System.out.print(name + ":" + o + ",value=" + o.toString());
        }
        System.out.println("");
    }


    private void printAttributes(MBeanServerConnection mbsc) {
        try {
            ObjectName objectName = new ObjectName("org.apache:group=\"Thread Pools\",name=GridServicesExecutor");
            AttributeList attributeList = mbsc.getAttributes(objectName, new String[]{"CorePoolSize", "MaximumPoolSize", "Shutdown"});
            List<Attribute> list = attributeList.asList();
            for (Attribute attribute : list) {
                Object o = attribute.getValue();
                String name = attribute.getName();
                System.out.println(name + ":" + o + ",type=" + o.getClass());
            }
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String address = "172.32.148.243:49290";
        long timeout = 1000L ;
        if (args.length == 2) {
            address = args[0];
            timeout = Long.parseLong(args[1]);
        }
        String baseUrl = "service:jmx:rmi:///jndi/rmi://" + address + "/jmxrmi";
        MBeanConnection mBeanConnection = new MBeanConnection(baseUrl,timeout);
        mBeanConnection.start();
    }
}
