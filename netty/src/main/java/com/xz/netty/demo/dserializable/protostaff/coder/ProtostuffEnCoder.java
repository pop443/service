package com.xz.netty.demo.dserializable.protostaff.coder;

import com.xz.netty.demo.dserializable.protostaff.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * protostaff 编码器
 */
public class ProtostuffEnCoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] body = ProtostuffUtil.serialize(msg) ;
        int bodyLength = body.length;  //读取消息的长度
        out.writeInt(bodyLength);  //先将消息长度写入，也就是消息头
        out.writeBytes(body);  //消息体中包含我们要发送的数据
    }
}
