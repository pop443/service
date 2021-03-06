package com.xz.netty.demo.asimple.server;

import com.xz.netty.util.NetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xz on 2020/1/25.
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SimpleServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf req = (ByteBuf)msg ;
        int length = req.readableBytes();
        byte[] receive = new byte[length] ;
        req.readBytes(receive) ;
        String getMsg = new String(receive, NetUtil.charsetName()) ;
        String retMsg = null ;
        if (getMsg.startsWith("hello\t")){
            retMsg = "good\t" ;
        }else{
            retMsg = "bad\t" ;
            logger.error("bad---receive-from-client:"+getMsg+";response:"+retMsg);
        }
        ByteBuf resp = Unpooled.copiedBuffer(retMsg.getBytes()) ;
        ctx.writeAndFlush(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.debug("---channelReadComplete---");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());
        ctx.close();
    }

}
