package com.xz.netty.demo.gcust.entity.transmission;

import java.util.Map;

/**
 * Created by xz on 2020/2/1.
 */
public class Header {
    /** 校验码 0xABEF+主版本号+次版本号 */
    private int crcCode ;
    /** 消息长度 请求头 请求体 */
    private int length ;
    /** 回话ID */
    private String sessionId ;
    /**
     * 0.业务请求
     * 1.业务响应
     * 2.业务oneway 即时请求又是响应
     * 3.接入请求
     * 4.接入响应
     * 5.心跳请求
     * 6.心跳响应
     * 9.异常
     */
    private byte type ;
    /** 优先级 */
    private byte priority ;
    /** 扩展消息 */
    private Map<String,Object> map ;

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Header{" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionId=" + sessionId +
                ", type=" + type +
                ", priority=" + priority +
                ", map.size=" + map.size() +
                '}';
    }
}
