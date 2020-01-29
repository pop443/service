package com.xz.netty.demo.dserializable.protostaff.client;

import com.xz.netty.demo.dserializable.protostaff.ProtostuffUtil;
import com.xz.netty.demo.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xz on 2020/1/25.
 */
public class ProtostuffClientHandle extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ProtostuffClientHandle.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 200; i++) {
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
        logger.info("receive-from-server:" + user);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("---channelReadComplete---");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage());
        ctx.close();
    }

}
