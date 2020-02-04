package com.xz.netty.demo.gcust.client;

import com.xz.netty.demo.gcust.coder.CustProtostuffDeCoder;
import com.xz.netty.demo.gcust.coder.CustProtostuffEnCoder;
import com.xz.netty.demo.gcust.common.exception.CustExceptionHandle;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 客户端通道初始化
 * 1.解码器
 * 2.编码器
 * 3.接入
 * 4.心跳
 * 5.业务
 */
public class CustClientChannelInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline() ;
        //反序列化及半包粘包处理
        channelPipeline.addLast("CustProtostuffDeCoder",new CustProtostuffDeCoder());
        //序列化
        channelPipeline.addLast("CustProtostuffEnCoder",new CustProtostuffEnCoder());
        //接入及请求中心处理器
        channelPipeline.addLast("CustClientAccessHandler",new CustClientAccessHandler());

        //异常中心处理器
        channelPipeline.addLast("CustExceptionHandle",new CustExceptionHandle());
    }
}
