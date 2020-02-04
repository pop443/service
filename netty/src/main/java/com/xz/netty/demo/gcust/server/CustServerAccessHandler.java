package com.xz.netty.demo.gcust.server;

import com.xz.netty.demo.gcust.entity.transmission.Header;
import com.xz.netty.demo.gcust.entity.transmission.HeaderType;
import com.xz.netty.demo.gcust.entity.transmission.NettyMessage;
import com.xz.netty.demo.gcust.entity.utils.HeaderUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 接入处理器
 */
public class CustServerAccessHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CustServerAccessHandler.class);
    private static Map<String,Boolean> map = new ConcurrentHashMap<>() ;
    private List<String> whiteIps = null;

    public CustServerAccessHandler() {
        whiteIps = new ArrayList<>() ;
        whiteIps.add("127.0.0.1") ;
        whiteIps.add("192.168.1.1") ;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage)msg ;
        Header header = nettyMessage.getHeader() ;
        byte headerType = header.getType() ;
        if (headerType==HeaderType.ACCESS_REQUEST){
            this.dealAccess(ctx,nettyMessage);
        }else if (headerType==HeaderType.HEARTBEAT_REQUEST){
            this.dealHeartBeat(ctx,nettyMessage);
        }else if (headerType==HeaderType.BUSINESS_REQUEST){
            super.channelRead(ctx,nettyMessage);
        }else if (headerType==HeaderType.BUSINESS_ALL){
            super.channelRead(ctx,nettyMessage);
        }else {
            nettyMessage.getHeader().setType(HeaderType.ERROR);
            ctx.writeAndFlush(nettyMessage);
        }
    }

    private void dealAccess(ChannelHandlerContext ctx,NettyMessage nettyMessage){
        InetSocketAddress inetSocketAddress = (InetSocketAddress)ctx.channel().remoteAddress() ;
        String address = inetSocketAddress.getHostName() ;
        byte retBody = -1 ;
        //重复登录验证
        if (!map.containsKey(address)&&whiteIps.contains(address)){
            map.put(address,true) ;
            retBody = 0 ;
        }
        logger.info(retBody==0?"--准备接入":"--不准接入");
        nettyMessage.getHeader().setType(HeaderType.ACCESS_RESPONSE);
        nettyMessage.setBody(retBody);
        ctx.writeAndFlush(nettyMessage);
    }
    private void dealHeartBeat(ChannelHandlerContext ctx,NettyMessage nettyMessage){
        Header header = nettyMessage.getHeader();
        HeaderUtil.buildHeartBeatResponse(header);
        byte result = -1 ;
        nettyMessage.setBody(result);
        ctx.writeAndFlush(nettyMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException){
            IOException ioException = (IOException)cause;
            String msg = ioException.getMessage() ;
            if (msg.contains("远程主机强迫关闭了一个现有的连接")){
                InetSocketAddress inetSocketAddress = (InetSocketAddress)ctx.channel().remoteAddress() ;
                String address = inetSocketAddress.getHostName() ;
                map.remove(address);
            }
        }
        super.exceptionCaught(ctx, cause);
    }
}
