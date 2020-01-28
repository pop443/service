package com.xz.netty.demo.dserializable.marshall.client;

import com.xz.netty.demo.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xz on 2020/1/25.
 */
public class SerializableClientHandle extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SerializableClientHandle.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 200; i++) {
            int index = i+1 ;
            User user = new User("name"+index,index) ;
            ctx.writeAndFlush(user);
            logger.info("index:" + (i + 1) + ";send--" + user);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        User user = (User) msg;
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
