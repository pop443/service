package com.xz.netty.demo.dserializable.marshall.client;

import com.xz.netty.demo.dserializable.marshall.MarshallingCodeCFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by xz on 2020/1/25.
 */
public class MarshallClientChannelInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildDecoder());
        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildEncoder());
        socketChannel.pipeline().addLast(new MarshallClientHandle());
    }
}
