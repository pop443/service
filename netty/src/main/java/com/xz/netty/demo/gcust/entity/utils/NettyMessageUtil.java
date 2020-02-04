package com.xz.netty.demo.gcust.entity.utils;

import com.xz.netty.demo.gcust.entity.transmission.Header;
import com.xz.netty.demo.gcust.entity.transmission.NettyMessage;

/**
 * Created by xz on 2020/2/1.
 */
public class NettyMessageUtil {
    public static NettyMessage buildHeartBeatRequest(){
        NettyMessage nettyMessage = new NettyMessage() ;
        Header header = HeaderUtil.buildHeartBeatRequest();
        nettyMessage.setHeader(header);
        return nettyMessage ;
    }

    public static NettyMessage buildAccessRequest() {
        NettyMessage nettyMessage = new NettyMessage() ;
        Header header = HeaderUtil.buildAccessRequest();
        nettyMessage.setHeader(header);
        return nettyMessage ;
    }
}
