package com.xz.netty.demo.eheartbeat.server;

import com.xz.netty.demo.dserializable.protostaff.ProtostuffUtil;
import com.xz.netty.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xz on 2020/1/25.
 */
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] bytes = (byte[])msg ;
        User user = null;
        try {
            user = ProtostuffUtil.deserialize(bytes,User.class);
            logger.debug("receive-from-client:"+user);
            user.setRemark("ok");
            ctx.writeAndFlush(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.debug("---channelReadComplete---");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());
        ctx.close();
    }
}
