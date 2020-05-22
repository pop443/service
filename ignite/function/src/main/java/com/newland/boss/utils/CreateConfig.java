package com.newland.boss.utils;

import java.io.*;

/**
 * Created by xz on 2020/5/11.
 */
public class CreateConfig {

    public static void main(String[] args) {
        for (int i = 1; i < 13; i++) {
            String config = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "\n" +
                    "<!--\n" +
                    "  Licensed to the Apache Software Foundation (ASF) under one or more\n" +
                    "  contributor license agreements.  See the NOTICE file distributed with\n" +
                    "  this work for additional information regarding copyright ownership.\n" +
                    "  The ASF licenses this file to You under the Apache License, Version 2.0\n" +
                    "  (the \"License\"); you may not use this file except in compliance with\n" +
                    "  the License.  You may obtain a copy of the License at\n" +
                    "\n" +
                    "       http://www.apache.org/licenses/LICENSE-2.0\n" +
                    "\n" +
                    "  Unless required by applicable law or agreed to in writing, software\n" +
                    "  distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                    "  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                    "  See the License for the specific language governing permissions and\n" +
                    "  limitations under the License.\n" +
                    "-->\n" +
                    "\n" +
                    "<beans xmlns=\"http://www.springframework.org/schema/beans\"\n" +
                    "       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "       xmlns:util=\"http://www.springframework.org/schema/util\"\n" +
                    "       xmlns:context=\"http://www.springframework.org/schema/context\"\n" +
                    "       xsi:schemaLocation=\"\n" +
                    "       http://www.springframework.org/schema/beans\n" +
                    "       http://www.springframework.org/schema/beans/spring-beans.xsd\n" +
                    "       http://www.springframework.org/schema/util\n" +
                    "       http://www.springframework.org/schema/util/spring-util.xsd\n" +
                    "        http://www.springframework.org/schema/context\n" +
                    "        http://www.springframework.org/schema/context/spring-context.xsd\">\n" +
                    "    <!--\n" +
                    "        Alter configuration below as needed.\n" +
                    "    -->\n" +
                    "\t<context:property-placeholder ignore-unresolvable=\"true\" location=\"file:/bosshome/bossapp/ignite/ignite/config/ip.properties\" />\n" +
                    "    <bean id=\"grid.cfg\" class=\"org.apache.ignite.configuration.IgniteConfiguration\">\n" +
                    "\t\t<!--工作目录-->\n" +
                    "        <property name=\"workDirectory\" value=\"/bosshome/bossapp/app_local/ignite/work/work"+i+"\"/>\n" +
                    "        <!--对等类加载是否启用，默认为false-->\n" +
                    "        <property name=\"peerClassLoadingEnabled\" value=\"true\"/>\n" +
                    "\t\t<!-- 对等类排除路径 EP用 -->\n" +
                    "\t\t<property name=\"PeerClassLoadingLocalClassPathExclude\">\n" +
                    "\t\t\t<list>\n" +
                    "                <value>com.newland.ignite.entryprocessor.*</value>\n" +
                    "                <value>com.newland.boss.script.performance.randomr.partitionEPget.*</value>\n" +
                    "                <value>com.newland.boss.script.features.event.*</value>\n" +
                    "                <value>com.newland.boss.script.performance.randomw.partitionsmallEPput.*</value>\n" +
                    "                <value>com.newland.ignite.continuousquery.*</value>\n" +
                    "            </list>\n" +
                    "\t\t</property>\n" +
                    "\t\t<!-- 部署模式 -->\n" +
                    "        <property name=\"deploymentMode\" value=\"SHARED\"/>\n" +
                    "\t\t<!-- 网络条件不佳 或负载过重时使用 默认为null-->\n" +
                    "\t\t<property name=\"SystemWorkerBlockedTimeout\" value=\"100000\"/>\n" +
                    "\t\t<!-- 网络条件不佳 或负载过重时修改 默认 10000-->\n" +
                    "\t\t<property name=\"FailureDetectionTimeout\" value=\"100000\"/>\n" +
                    "\n" +
                    "\t\t<!--系统线程池大小 处理所有与缓存相关的操作，除了SQL以及其它的查询类型 默认8或CPU -->\n" +
                    "        <property name=\"systemThreadPoolSize\" value=\"20\"/>\n" +
                    "\t\t<!--管理线程池大小 负责处理内核和Visor客户端消息 默认4 -->\n" +
                    "        <property name=\"managementThreadPoolSize\" value=\"4\"/>\n" +
                    "        <!--公共线程池大小 负责Ignite的计算网格 默认8或CPU -->\n" +
                    "        <property name=\"publicThreadPoolSize\" value=\"20\"/>\n" +
                    "\t\t<!--查询线程池大小 处理集群内所有的SQL、扫描和SPI查询 默认8或CPU -->\n" +
                    "        <property name=\"queryThreadPoolSize\" value=\"20\"/>\n" +
                    "\t\t<!--服务线程池大小 负责Ignite的服务网格 与公共线程池大小一致 -->\n" +
                    "        <property name=\"serviceThreadPoolSize\" value=\"4\"/>\n" +
                    "\t\t<!--并行线程池大小 显著加速基本的缓存操作以及事务 EP等 默认8或CPU -->\n" +
                    "        <property name=\"stripedPoolSize\" value=\"20\"/>\n" +
                    "\t\t<!--数据流线程池大小 处理IgniteDataStreamer流数据 默认8或CPU -->\n" +
                    "        <property name=\"dataStreamerThreadPoolSize\" value=\"20\"/>\n" +
                    "        <!--平衡线程池大小 缓存再平衡用 默认4-->\n" +
                    "        <property name=\"rebalanceThreadPoolSize\" value=\"4\"/>\n" +
                    "\t\t<!--对等类加载线程池大小 加载远端类 默认2 -->\n" +
                    "\t\t<property name=\"peerClassLoadingThreadPoolSize\" value=\"2\"/>\n" +
                    "\t\t<!--异步回调线程池大小 缓存持续查询过滤功能 默认8或CPU -->\n" +
                    "\t\t<property name=\"asyncCallbackPoolSize\" value=\"4\"/>\n" +
                    "        <!--用户验证是否开启 默认为false 开启后默认用户名密码都是ignite-->\n" +
                    "        <property name=\"authenticationEnabled\" value=\"false\"/>\n" +
                    "        <property name=\"gridLogger\" >\n" +
                    "            <bean class=\"org.apache.ignite.logger.log4j.Log4JLogger\">\n" +
                    "                <constructor-arg type=\"java.lang.String\" value=\"/bosshome/bossapp/ignite/ignite/config/node-"+i+"-log4j.xml\"/>\n" +
                    "            </bean>\n" +
                    "        </property>\n" +
                    "        <!--数据存储配置 -->\n" +
                    "        <property name=\"dataStorageConfiguration\">\n" +
                    "            <bean class=\"org.apache.ignite.configuration.DataStorageConfiguration\">\n" +
                    "                <property name=\"pageSize\" value=\"#{4 * 1024}\"/>\n" +
                    "\t\t\t\t <!-- 并发级别 level 设置为CPU数量-->\n" +
                    "\t\t\t\t<property name=\"concurrencyLevel\" value=\"${concurrencyLevel}\"/>\n" +
                    "\t\t\t\t<!-- 写入优化 -->\n" +
                    "                <property name=\"writeThrottlingEnabled\" value=\"true\"/>\n" +
                    "\t\t\t\t<!-- 数据存储指标 -->\n" +
                    "                <property name=\"metricsEnabled\" value=\"false\"/>\n" +
                    "                <!--数据分布配置 默认是都存放到内存中，此处进行持久化 -->\n" +
                    "                <property name=\"defaultDataRegionConfiguration\">\n" +
                    "                    <bean class=\"org.apache.ignite.configuration.DataRegionConfiguration\">\n" +
                    "                        <!--是否持久化到磁盘 true为持久化 -->\n" +
                    "                        <property name=\"persistenceEnabled\" value=\"false\"/>\n" +
                    "                        <property name=\"name\" value=\"default_Region\"/>\n" +
                    "                        <!-- 4G initial size. 初始化内存-->\n" +
                    "                        <property name=\"initialSize\" value=\"#{8L * 1024 * 1024 * 1024}\" />\n" +
                    "                        <!-- 10G maximum size. 最大内存大小-->\n" +
                    "                        <property name=\"maxSize\" value=\"#{8L * 1024 * 1024 * 1024}\" />\n" +
                    "                        <property name=\"checkpointPageBufferSize\" value=\"#{1L *1024* 1024 * 1024}\" />\n" +
                    "                        <property name=\"pageEvictionMode\" value=\"RANDOM_2_LRU\"/>\n" +
                    "                        <property name=\"evictionThreshold\" value=\"0.7\"/>\n" +
                    "\t\t\t\t\t\t<!-- 数据区指标  -->\n" +
                    "                        <property name=\"metricsEnabled\" value=\"false\"/>\n" +
                    "                    </bean>\n" +
                    "                </property>\n" +
                    "\t\t\t\t\n" +
                    "                \n" +
                    "                <!-- Defining several data regions for different memory regions 持久化数据存储目录 ******注意该地方目录需修改成自己的****** -->\n" +
                    "\t\t\t\t\n" +
                    "                <property name=\"storagePath\" value=\"/bosshome/bossapp/app_local/ignite/data/data"+i+"/storage\" />\n" +
                    "                <property name=\"walArchivePath\" value=\"/bosshome/bossapp/app_local/ignite/data/data"+i+"/walArchive\" />\n" +
                    "                <property name=\"walPath\" value=\"/bosshome/bossapp/app_local/ignite/data/data"+i+"/wal\" />\n" +
                    "                <property name=\"walSegments\" value=\"4\"/>\n" +
                    "\t\t\t\t<property name=\"maxWalArchiveSize\" value=\"#{2 * 1024 * 1024 * 1024}\"/>\n" +
                    "\t\t\t\t<property name=\"walSegmentSize\" value=\"#{ 512 * 1024 * 1024}\"/>\n" +
                    "\t\t\t\t<property name=\"walMode\" value=\"NONE\"/>\n" +
                    "            </bean>\t\t\t\n" +
                    "        </property>\n" +
                    "        <property name=\"localHost\" value=\"${ip}\"/>\n" +
                    "\t\t<property name=\"transactionConfiguration\">\n" +
                    "\t\t\t<bean class=\"org.apache.ignite.configuration.TransactionConfiguration\">\n" +
                    "\t\t\t<!--长期运行事务阻断分区映射交换的最大时间，时间一到，所有的未完成事务都会回滚，让分区映射交换进程先完成 配置5秒 默认为0-->\n" +
                    "\t\t\t<property name=\"TxTimeoutOnPartitionMapExchange\" value=\"5000\"/>\n" +
                    "\t\t\t</bean>\n" +
                    "\t\t</property>\n" +
                    "\t\t<!-- 系统分区丢失事件配置 \n" +
                    "\t\t<property name=\"includeEventTypes\">\n" +
                    "            <list>\n" +
                    "                <util:constant static-field=\"org.apache.ignite.events.EventType.EVT_CACHE_REBALANCE_PART_DATA_LOST\"/>\n" +
                    "                <util:constant static-field=\"org.apache.ignite.events.EventType.EVT_CACHE_REBALANCE_STARTED\"/>\n" +
                    "                <util:constant static-field=\"org.apache.ignite.events.EventType.EVT_CACHE_REBALANCE_STOPPED\"/>\n" +
                    "            </list>\n" +
                    "        </property>-->\n" +
                    "        <!-- Ignite自己本身有发现机制，只需要配置静态IP即可相互发现；单机只需要配置自己即可-->\n" +
                    "        <property name=\"discoverySpi\">\n" +
                    "            <bean class=\"org.apache.ignite.spi.discovery.zk.ZookeeperDiscoverySpi\">\n" +
                    "                <property name=\"zkConnectionString\" value=\"10.32.48.26:2902,10.32.48.27:2902,10.32.48.28:2902\"/>\n" +
                    "                <property name=\"sessionTimeout\" value=\"60000\"/>\n" +
                    "                <property name=\"zkRootPath\" value=\"/ignite1\"/>\n" +
                    "                <property name=\"joinTimeout\" value=\"30000\"/>\n" +
                    "            </bean>\n" +
                    "        </property>\n" +
                    "\t<property name=\"communicationSpi\">\n" +
                    "            <bean class=\"org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi\">\n" +
                    "                <property name=\"localAddress\" value=\"${ip}\" />\n" +
                    "                <property name=\"usePairedConnections\" value=\"true\"/>\n" +
                    "                <property name=\"slowClientQueueLimit\" value=\"10000\"/>\n" +
                    "                <!--连接超时时长-->\n" +
                    "                <property name=\"connectTimeout\" value=\"60000\" />\n" +
                    "                <property name=\"reconnectCount\" value=\"3\"/>\n" +
                    "            </bean>\n" +
                    "        </property>\n" +
                    "        <!--网络超时时长 默认5000-->\n" +
                    "        <property name=\"networkTimeout\" value=\"60000\"/>\n" +
                 /*   "\t\t<property name=\"lifecycleBeans\">\n" +
                    "\t\t\t<list>\n" +
                    "\t\t\t\t<bean class=\"com.newland.boss.script.performance.loaddata.LoadData\"/>\n" +
                    "\t\t\t</list>\n" +
                    "\t\t</property>\n"+
                    "\t</bean>\n" +
                    "\t\n" +
                    "\t<bean id=\"custDataSource\" class=\"com.newland.ignite.datasource.CustDataSource\" init-method=\"init\" destroy-method=\"close\">\n" +
                    "        <constructor-arg name=\"paths\">\n" +
                    "            <array>\n" +
                    "                <value>/bosslog/ignite/ignite/config/bossdbsource.properties</value>\n" +
                    "            </array>\n" +
                    "        </constructor-arg>\n" +*/
                    "    </bean>\n" +
                    "\t\n" +
                    "</beans>\n" +
                    "\n" ;
            String basePath = "F:\\work\\boss\\2020\\ignite_test\\igntie测试用例及结果\\结果\\第四版\\环境\\配置无持久化";
            FileWriter writer;
            try {
                writer = new FileWriter(basePath+"\\node-boss"+i+"-config.xml");
                writer.write(config);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("ok"+i);
        }

    }
}
