package com.xz.netty.demo.asimple.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by xz on 2020/1/25.
 */
public class SimpleClientChannelInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new SimpleClientHandle("hello\t"));
    }
}
