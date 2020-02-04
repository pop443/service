package com.xz.netty.demo.gcust.exception;

/**
 * Created by xz on 2020/2/2.
 */
public class NettyMessageException extends Exception {
    private int code;
    private String message;

    public NettyMessageException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
