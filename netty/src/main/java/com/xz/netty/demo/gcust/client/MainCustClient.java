package com.xz.netty.demo.gcust.client;

import com.xz.netty.util.NetUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2020/1/24.
 */
public class MainCustClient {
    private static final Logger logger = LoggerFactory.getLogger(MainCustClient.class);

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void start() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        boolean bo = false ;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    //禁用了Nagle算法 数据不缓存直接发送
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new CustClientChannelInit());
            // 启动客户端
            ChannelFuture f = bootstrap.connect(NetUtil.getHost(), NetUtil.getPort()).sync();
            // 等待连接关闭
            f.channel().closeFuture().sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    logger.error("主程序事件"+future.getClass());
                }
            });
        } catch (Exception e) {
            bo = true ;
            logger.error("主程序异常"+e.getMessage());
        } finally {
            //在重连前 必须要关闭现存资源的占用
            workerGroup.shutdownGracefully();
            if (bo){
                ReConnectThread reConnectThread = new ReConnectThread(this) ;
                executorService.execute(reConnectThread);
            }
        }
    }

    public static void main(String[] args) {
        try {
            MainCustClient mainReConnectClient = new MainCustClient();
            mainReConnectClient.start();
        } catch (Exception e) {
            logger.error("main--"+e.getMessage());
        }
    }
}
