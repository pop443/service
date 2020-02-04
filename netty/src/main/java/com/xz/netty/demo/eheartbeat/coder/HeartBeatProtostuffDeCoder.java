package com.xz.netty.demo.eheartbeat.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by xz on 2020/1/29.
 */
public class HeartBeatProtostuffDeCoder extends ByteToMessageDecoder {
    private int HEAD_LENGTH = 4 ;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < HEAD_LENGTH) {  //这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于上面我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
            System.out.println("4位长度头读取失败");
            return;
        }
        in.markReaderIndex();                  //我们标记一下当前的readIndex的位置
        int bodyLength = in.readInt();       // 读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4
        if (bodyLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
            System.out.println("异常数据");
            ctx.close();
        }else if (bodyLength == 0){
            System.out.println("心跳数据");
        }
        if (in.readableBytes() < bodyLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
            in.resetReaderIndex();
            System.out.println("半包");
            return;
        }

        byte[] body = new byte[bodyLength];  //  嗯，这时候，我们读到的长度，满足我们的要求了，把传送过来的数据，取出来吧~~
        in.readBytes(body);  //
        out.add(body);
    }
}
