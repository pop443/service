package com.xz.netty.demo.freconnect.client;

import com.xz.netty.demo.dserializable.protostaff.ProtostuffUtil;
import com.xz.netty.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by xz on 2020/1/25.
 */
public class ReConnectClientHandle extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ReConnectClientHandle.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Random random = new Random() ;
        for (int i = 0; i < 20; i++) {
            int index = i+1 ;
            User user = new User("什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事什么事"+index,index) ;
            ctx.writeAndFlush(user);
            logger.debug("index:" + (i + 1) + ";send--" + user);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        byte[] bytes = (byte[])msg ;
        User user = ProtostuffUtil.deserialize(bytes,User.class);
        logger.debug("receive-from-server:" + user);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.debug("---channelReadComplete---");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage());
        ctx.close();
    }

}
