package com.xz.netty.demo.cspecial.server;

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
public class SpecialServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SpecialServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String getMsg = (String)msg ;
        String retMsg = null ;
        String index = getMsg.replace("hello","");
        if (getMsg.endsWith("hello")){
            retMsg = index+"good"+ SpecialDelimiter.delimiter;
        }else{
            retMsg = index+"bad"+ SpecialDelimiter.delimiter ;
        }
        ByteBuf resp = Unpooled.copiedBuffer(retMsg.getBytes()) ;
        ctx.writeAndFlush(resp);
        logger.info("receive-from-client:"+getMsg+";response:"+retMsg);
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
