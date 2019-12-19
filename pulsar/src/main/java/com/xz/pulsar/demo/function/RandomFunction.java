package com.xz.pulsar.demo.function;

import com.xz.pulsar.demo.entity.User;
import com.xz.pulsar.utils.PulsarConntionUtil;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.BatchMessageIdImpl;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;

/**
1.创建 persistent://pulsar/xz/function topic
2.提交function
bin/pulsar-admin functions create \
 --jar examples/xz/pulsar-1.0-SNAPSHOT.jar \
 --classname com.xz.pulsar.demo.function.RandomFunction \
 --tenant pulsar \
 --namespace xz \
 --name random \
 --inputs persistent://pulsar/xz/function

3.启动函数
bin/pulsar-admin functions start --tenant pulsar --namespace xz --name random
4.停止函数
bin/pulsar-admin functions stop --tenant pulsar --namespace xz --name random
5.删除函数
bin/pulsar-admin functions delete --tenant pulsar --namespace xz --name random
6.查看函数信息
bin/pulsar-admin functions get --tenant pulsar --namespace xz --name random
7.查看函数运行状态
bin/pulsar-admin functions getstatus --tenant pulsar --namespace xz --name random

 */
public class RandomFunction implements Function<User,User> {
    @Override
    public User process(User user, Context context) throws Exception {
        Logger logger = context.getLogger() ;
        try {
            String value = user.getAccount()+user.getPassword() ;
            logger.info("------------------------------"+value);
            context.newOutputMessage("persistent://pulsar/xz/demo1",Schema.BYTES).value(value.getBytes()).send() ;
        } catch (PulsarClientException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }


}
