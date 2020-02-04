package com.xz.netty.demo.gcust.client;

import com.xz.netty.demo.gcust.entity.transmission.NettyMessage;
import com.xz.netty.demo.gcust.entity.utils.NettyMessageUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xz on 2020/2/4.
 */
public class CustClientHearBeatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CustClientHearBeatHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            NettyMessage nettyMessage = NettyMessageUtil.buildHeartBeatRequest() ;
            ctx.writeAndFlush(nettyMessage);
            logger.info("心跳监测");
        }
    }
}
