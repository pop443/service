package com.xz.netty.demo.gcust.client;

import com.xz.netty.demo.dserializable.protostaff.ProtostuffUtil;
import com.xz.netty.demo.gcust.entity.transmission.NettyMessage;
import com.xz.netty.demo.gcust.entity.utils.HeaderUtil;
import com.xz.netty.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xz on 2020/1/25.
 */
public class CustBusinessClientHandle extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CustBusinessClientHandle.class);

    public CustBusinessClientHandle(ChannelHandlerContext ctx) {
        for (int i = 0; i < 20; i++) {
            int index = i+1 ;
            User user = new User("什么事"+index,index) ;
            NettyMessage nettyMessage = new NettyMessage() ;
            nettyMessage.setHeader(HeaderUtil.buildBusinessRequest());
            nettyMessage.setBody(user);
            ctx.writeAndFlush(nettyMessage);
            logger.debug("index:" + (i + 1) + ";send--" + user);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        NettyMessage nettyMessage = (NettyMessage)msg ;
        logger.debug("receive-from-server:" + nettyMessage.getBody().toString());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.debug("---channelReadComplete---");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }

}
