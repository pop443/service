package com.xz.netty.demo.gcust.common.exception;

import com.xz.netty.demo.gcust.exception.NettyMessageException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常汇总处理
 */
public class CustExceptionHandle extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CustExceptionHandle.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof NettyMessageException){
            NettyMessageException exception = (NettyMessageException)cause ;
            if (exception.getCode()<0){
                ctx.channel().close();
                logger.error(exception.getCode()+"--"+exception.getMessage());
            }
        }
        //cause.printStackTrace();
    }
}
