package com.xz.netty.demo.freconnect.server;

import com.xz.netty.demo.dserializable.protostaff.ProtostuffUtil;
import com.xz.netty.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xz on 2020/1/25.
 */
public class ReConnectServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ReConnectServerHandler.class);

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
            ctx.writeAndFlush(new User("fuck",2));
            logger.error("心跳发送假的");
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
