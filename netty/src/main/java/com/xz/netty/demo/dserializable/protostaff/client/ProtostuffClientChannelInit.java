package com.xz.netty.demo.dserializable.protostaff.client;

import com.xz.netty.demo.dserializable.protostaff.coder.ProtostuffDeCoder;
import com.xz.netty.demo.dserializable.protostaff.coder.ProtostuffEnCoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by xz on 2020/1/25.
 */
public class ProtostuffClientChannelInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new ProtostuffDeCoder());
        socketChannel.pipeline().addLast(new ProtostuffEnCoder());
        socketChannel.pipeline().addLast(new ProtostuffClientHandle());
    }
}
