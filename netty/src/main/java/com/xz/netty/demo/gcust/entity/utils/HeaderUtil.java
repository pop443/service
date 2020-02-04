package com.xz.netty.demo.gcust.entity.utils;

import com.xz.netty.demo.gcust.entity.transmission.Header;
import com.xz.netty.demo.gcust.entity.transmission.HeaderType;
import com.xz.netty.demo.gcust.entity.transmission.NettyMessage;

/**
 * Created by xz on 2020/2/1.
 */
public class HeaderUtil {

    public static Header buildAccessRequest() {
        Header header = new Header() ;
        header.setType(HeaderType.ACCESS_REQUEST);
        return header ;
    }

    public static Header buildHeartBeatRequest() {
        Header header = new Header() ;
        header.setType(HeaderType.HEARTBEAT_REQUEST);
        return header ;
    }
    public static Header buildHeartBeatResponse() {
        Header header = new Header() ;
        buildHeartBeatResponse(header) ;
        return header ;
    }
    public static void buildHeartBeatResponse(Header header) {
        header.setType(HeaderType.HEARTBEAT_RESPONSE);
    }
    public static Header buildBusinessRequest() {
        Header header = new Header() ;
        header.setType(HeaderType.BUSINESS_REQUEST);
        return header ;
    }
    public static void buildBusinessResponse(Header header) {
        header.setType(HeaderType.BUSINESS_RESPONSE);
    }
}
