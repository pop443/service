package com.xz.netty.demo.asimple.client;

import com.xz.netty.demo.asimple.server.SimpleServerHandler;
import com.xz.netty.util.NetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by xz on 2020/1/25.
 */
public class SimpleClientHandle extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SimpleClientHandle.class);
    private String msg ;

    public SimpleClientHandle(String msg) {
        this.msg = msg;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf byteBuf = Unpooled.copiedBuffer(msg.getBytes());
        ctx.writeAndFlush(byteBuf);
        logger.info("length:"+msg.getBytes().length+"---channelActive---");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg; // (1)
        try {
            int length = byteBuf.readableBytes() ;
            byte[] fromServer = new byte[length] ;
            byteBuf.readBytes(fromServer) ;
            logger.info("length:"+length+"---channelRead:"+new String(fromServer, NetUtil.charsetName()));
            System.out.println();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            byteBuf.release();
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("---channelReadComplete---");
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info("---exceptionCaught---");
        cause.printStackTrace();
        ctx.close();
    }

}
