package com.xz.netty.demo.gcust.client;

import com.xz.netty.demo.gcust.entity.transmission.HeaderType;
import com.xz.netty.demo.gcust.entity.transmission.NettyMessage;
import com.xz.netty.demo.gcust.entity.utils.NettyMessageUtil;
import com.xz.netty.demo.gcust.exception.NettyMessageException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


/**
 * 接入
 * a:客户端发送接入请求
 * b:所有接收的入口
 */
public class CustClientAccessHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CustClientAccessHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyMessage nettyMessage = NettyMessageUtil.buildAccessRequest();
        ctx.writeAndFlush(nettyMessage);
        logger.info("-------开始接入-------");
        super.channelActive(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage)msg ;
        if (nettyMessage==null || nettyMessage.getHeader()==null||nettyMessage.getHeader().getType()== HeaderType.ERROR){
            throw new NettyMessageException(0,"报文出错 异常") ;
        }
        if (nettyMessage.getHeader().getType()== HeaderType.ACCESS_RESPONSE){
            byte result = (byte)nettyMessage.getBody() ;
            if (result==0){
                //增加心跳链
                ChannelPipeline channelPipeline = ctx.pipeline() ;
                //channelPipeline.addLast("IdleStateHandler",new IdleStateHandler(0,0,4, TimeUnit.SECONDS));
                //channelPipeline.addLast("CustClientHearBeatHandler",new CustClientHearBeatHandler());
                //确定已经接入成功 透传业务
                //业务处理器
                channelPipeline.addLast("CustBusinessClientHandle",new CustBusinessClientHandle(ctx));

            }else {
                throw new NettyMessageException(-1,"接入失败") ;
            }

        }else if (nettyMessage.getHeader().getType()== HeaderType.HEARTBEAT_RESPONSE){
            logger.info("收到心跳");
        }else if (nettyMessage.getHeader().getType()== HeaderType.BUSINESS_RESPONSE){
            super.channelRead(ctx,nettyMessage);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("CustClientAccessHandler异常："+cause.getMessage());
        //透传
        super.exceptionCaught(ctx, cause);
    }
}
