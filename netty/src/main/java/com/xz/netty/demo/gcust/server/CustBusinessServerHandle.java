package com.xz.netty.demo.gcust.server;

import com.xz.netty.demo.gcust.entity.transmission.BaseBusiness;
import com.xz.netty.demo.gcust.entity.transmission.HeaderType;
import com.xz.netty.demo.gcust.entity.transmission.NettyMessage;
import com.xz.netty.demo.gcust.entity.utils.HeaderUtil;
import com.xz.netty.demo.gcust.exception.NettyMessageException;
import com.xz.netty.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xz on 2020/2/3.
 */
public class CustBusinessServerHandle extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CustBusinessServerHandle.class);

    /**
     * 业务处理 不需要透传
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage)msg ;
        if (nettyMessage==null || nettyMessage.getHeader()==null || nettyMessage.getHeader().getType()!= HeaderType.BUSINESS_REQUEST){
            throw new NettyMessageException(0,"业务处理 异常");
        }
        HeaderUtil.buildBusinessResponse(nettyMessage.getHeader());
        User user = (User)nettyMessage.getBody() ;
        user.setRemark("server");
        ctx.writeAndFlush(nettyMessage);

    }
}
