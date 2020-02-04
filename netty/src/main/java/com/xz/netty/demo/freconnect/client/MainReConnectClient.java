package com.xz.netty.demo.freconnect.client;

import com.xz.netty.util.NetUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2020/1/24.
 */
public class MainReConnectClient {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void start() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    //禁用了Nagle算法 数据不缓存直接发送
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ReConnectClientChannelInit());
            // 启动客户端
            ChannelFuture f = bootstrap.connect(NetUtil.getHost(), NetUtil.getPort()).sync();
            // 等待连接关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            //在重连前 必须要关闭现存资源的占用
            workerGroup.shutdownGracefully();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("开始重连");
                        start();
                    } catch (Exception e) {
                        System.out.println("thread--"+e.getMessage());
                    }
                }
            });

        }
    }

    public static void main(String[] args) {
        try {
            MainReConnectClient mainReConnectClient = new MainReConnectClient();
            mainReConnectClient.start();
        } catch (Exception e) {
            System.out.println("main--"+e.getMessage());
        }
    }
}
