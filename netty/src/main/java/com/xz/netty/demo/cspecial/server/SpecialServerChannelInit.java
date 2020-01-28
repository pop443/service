package com.xz.netty.demo.cspecial.server;

import com.xz.netty.demo.cspecial.SpecialDelimiter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


/**
 * Created by xz on 2020/1/25.
 */
public class SpecialServerChannelInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ByteBuf byteBuf = Unpooled.copiedBuffer(SpecialDelimiter.delimiter.getBytes());
        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,byteBuf));
        //socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10));
        socketChannel.pipeline().addLast(new StringDecoder());
        socketChannel.pipeline().addLast(new SpecialServerHandler());
    }
}
