package com.xz.netty.demo.eheartbeat.client;

import com.xz.netty.demo.eheartbeat.coder.HeartBeatProtostuffDeCoder;
import com.xz.netty.demo.eheartbeat.coder.HeartBeatProtostuffEnCoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2020/1/25.
 */
public class HeartBeatClientChannelInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //客户端发送心跳
        socketChannel.pipeline().addLast(new IdleStateHandler(0,0,4, TimeUnit.SECONDS));
        socketChannel.pipeline().addLast(new HeartBeatHandler());
        socketChannel.pipeline().addLast(new HeartBeatProtostuffDeCoder());
        socketChannel.pipeline().addLast(new HeartBeatProtostuffEnCoder());
        socketChannel.pipeline().addLast(new HeartBeatClientHandle());
    }
}
