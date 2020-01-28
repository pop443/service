package com.xz.netty.demo.cspecial.client;

import com.xz.netty.demo.cspecial.SpecialDelimiter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xz on 2020/1/25.
 */
public class SpecialClientHandle extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SpecialClientHandle.class);
    private String msg ;

    public SpecialClientHandle(String msg) {
        this.msg = msg;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 200; i++) {
            int index = i+1 ;
            String msg = index+this.msg+ SpecialDelimiter.delimiter ;
            ByteBuf byteBuf = Unpooled.copiedBuffer(msg.getBytes());
            ctx.writeAndFlush(byteBuf);
            logger.debug("index:" + (i + 1) + ";send--" + msg);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String reveiceMsg = (String) msg;
        logger.info("receive-from-server:" + reveiceMsg);
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
