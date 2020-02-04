package com.xz.netty.demo.freconnect.server;

import com.xz.netty.demo.eheartbeat.coder.HeartBeatProtostuffDeCoder;
import com.xz.netty.demo.eheartbeat.coder.HeartBeatProtostuffEnCoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;


/**
 * Created by xz on 2020/1/25.
 */
public class ReConnectServerChannelInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new HeartBeatProtostuffDeCoder());
        socketChannel.pipeline().addLast(new HeartBeatProtostuffEnCoder());
        socketChannel.pipeline().addLast(new ReConnectServerHandler());
    }
}
