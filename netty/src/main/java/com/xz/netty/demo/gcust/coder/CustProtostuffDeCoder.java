package com.xz.netty.demo.gcust.coder;

import com.xz.netty.demo.dserializable.protostaff.ProtostuffUtil;
import com.xz.netty.demo.gcust.entity.transmission.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * protostaff 反序列化
 */
public class CustProtostuffDeCoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(CustProtostuffDeCoder.class);

    private int HEAD_LENGTH = 4 ;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < HEAD_LENGTH) {  //这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于上面我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
            logger.error("4位长度头读取失败 非netty消息 连接关闭");
            ctx.close();
            return;
        }
        in.markReaderIndex();                  //我们标记一下当前的readIndex的位置
        int bodyLength = in.readInt();       // 读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4
        if (bodyLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
            logger.error("异常数据");
            ctx.close();
        }
        if (in.readableBytes() < bodyLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
            in.resetReaderIndex();
            logger.info("出现半包等待下一次数据传输");
            return;
        }

        byte[] body = new byte[bodyLength];  //  嗯，这时候，我们读到的长度，满足我们的要求了，把传送过来的数据，取出来吧~~
        in.readBytes(body);  //
        NettyMessage nettyMessage = ProtostuffUtil.deserialize(body,NettyMessage.class) ;
        if (nettyMessage==null || nettyMessage.getHeader()==null){
            logger.info("包传输错误 连接关闭");
            ctx.close() ;
        }
        out.add(nettyMessage);
    }
}
