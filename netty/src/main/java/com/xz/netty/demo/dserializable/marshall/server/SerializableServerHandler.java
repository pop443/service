package com.xz.netty.demo.dserializable.marshall.server;

import com.xz.netty.demo.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xz on 2020/1/25.
 */
public class SerializableServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SerializableServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        User user = (User)msg ;
        logger.info("receive-from-client:"+user);
        user.setRemark("ok");
        ctx.writeAndFlush(user);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("---channelReadComplete---");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());
        ctx.close();
    }
}
