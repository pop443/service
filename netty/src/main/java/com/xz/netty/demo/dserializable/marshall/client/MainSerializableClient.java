package com.xz.netty.demo.dserializable.marshall.client;

import com.xz.netty.util.NetUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by xz on 2020/1/24.
 */
public class MainSerializableClient {

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    //禁用了Nagle算法 数据不缓存直接发送
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new SerializableClientChannelInit());
            // 启动客户端
            ChannelFuture f = bootstrap.connect(NetUtil.getHost(), NetUtil.getPort()).sync();
            // 等待连接关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
