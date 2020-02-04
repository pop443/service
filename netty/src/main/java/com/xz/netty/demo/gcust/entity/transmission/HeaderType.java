package com.xz.netty.demo.gcust.entity.transmission;

/**
 *
 0.业务请求
 1.业务响应
 2.业务oneway 即时请求又是响应
 3.接入请求
 4.接入响应
 5.心跳请求
 6.心跳响应
 */
public class HeaderType {
    public static byte BUSINESS_REQUEST = 0 ;
    public static byte BUSINESS_RESPONSE = 1 ;
    public static byte BUSINESS_ALL = 2 ;
    public static byte ACCESS_REQUEST = 3 ;
    public static int i = 3 ;
    public static byte ACCESS_RESPONSE = 4 ;
    public static byte HEARTBEAT_REQUEST = 5 ;
    public static byte HEARTBEAT_RESPONSE = 6 ;
    public static byte ERROR = 9 ;
}
