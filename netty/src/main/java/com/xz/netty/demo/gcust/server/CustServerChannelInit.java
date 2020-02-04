package com.xz.netty.demo.gcust.server;

import com.xz.netty.demo.gcust.coder.CustProtostuffDeCoder;
import com.xz.netty.demo.gcust.coder.CustProtostuffEnCoder;
import com.xz.netty.demo.gcust.common.exception.CustExceptionHandle;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * Created by xz on 2020/1/25.
 */
public class CustServerChannelInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline() ;
        //反序列化及半包粘包处理
        channelPipeline.addLast("CustProtostuffDeCoder",new CustProtostuffDeCoder());
        //序列化
        channelPipeline.addLast("CustProtostuffEnCoder",new CustProtostuffEnCoder());
        //接入及请求中心处理器
        channelPipeline.addLast("CustServerAccessHandler",new CustServerAccessHandler());
        //业务处理
        channelPipeline.addLast("CustBusinessServerHandle",new CustBusinessServerHandle());
        //异常中心处理器
        channelPipeline.addLast("CustExceptionHandle",new CustExceptionHandle());
    }
}
