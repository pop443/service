package com.xz.ignite.basefunction.metrics;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/1/16.
 */
public class MBeanConnection {

    public static void main(String[] args) {
        try {
            JMXServiceURL url = new JMXServiceURL(
                    "service:jmx:rmi:///jndi/rmi://172.32.148.244:49000/jmxrmi");
            Map<String,Object> map = new HashMap<>() ;
            map.put(JMXConnector.CREDENTIALS,new String[]{"root","root123."}) ;
            map.put("jmx.remote.x.request.waiting.timeout","3000") ;
            map.put("jmx.remote.x.notification.fetch.timeout","3000") ;
            map.put("sun.rmi.transport.connectionTimeout","3000") ;
            map.put("sun.rmi.transport.tcp.handshakeTimeout","3000") ;
            map.put("sun.rmi.transport.tcp.responseTimeout","3000") ;
            JMXConnector jmxc = JMXConnectorFactory.connect(url, map);
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

            // 把所有Domain都打印出来
            printAttributes(mbsc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printAttributes(MBeanServerConnection mbsc){
        try {
            ObjectName objectName = new ObjectName("org.apache:group=\"Thread Pools\",name=GridServicesExecutor") ;
            AttributeList attributeList = mbsc.getAttributes(objectName,new String[]{"CorePoolSize", "MaximumPoolSize", "Shutdown"}) ;
            List<Attribute> list = attributeList.asList() ;
            for (Attribute attribute:list) {
                Object o = attribute.getValue() ;
                String name = attribute.getName() ;
                System.out.println(name+":"+o);
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
}
