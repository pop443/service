package com.xz.netty.demo.eheartbeat.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * Created by xz on 2020/1/30.
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            System.out.println(event.state());
            int heart = 0 ;
            ByteBuf byteBuf = Unpooled.buffer(4);
            byteBuf.writeInt(heart) ;
            ctx.writeAndFlush(byteBuf);
            System.out.println("心跳监测");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("心跳监测ok");
        super.channelReadComplete(ctx);
    }
}
