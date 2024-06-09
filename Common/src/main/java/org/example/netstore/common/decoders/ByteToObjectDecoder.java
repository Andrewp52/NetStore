package org.example.netstore.common.decoders;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.util.AttributeKey;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ByteToObjectDecoder extends SimpleChannelInboundHandler {
    private final AttributeKey<ByteBuf> dataKey = AttributeKey.valueOf("dataBuf");

    /**
     Inbound buffer may not contain all data.
     Additional DataBuf in attributes and procedure in channelRead0
     is used for catching such cases and receive all data in separated messages.
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bb = (ByteBuf) msg;
        ByteBuf dataBuf = ctx.attr(dataKey).get();
        while (bb.readerIndex() != bb.writerIndex()){
            if(dataBuf == null){
                int size = bb.readInt();
                dataBuf = ctx.alloc().directBuffer(size, size);
                ctx.attr(dataKey).set(dataBuf);
            }
            if(dataBuf.writerIndex() < dataBuf.capacity()){
                bb.readBytes(dataBuf, Math.min(bb.readableBytes(), dataBuf.writableBytes()));
            }
            if(dataBuf.writerIndex() == dataBuf.capacity()) {
                Object decoded = getFromByteBuf(dataBuf);
                dataBuf = null;
                ctx.attr(dataKey).set(null);
                ctx.fireChannelRead(decoded);
            }
        }
    }


    private Object getFromByteBuf(ByteBuf buf) throws IOException {
        Object decoded;
        try (
                ByteArrayInputStream bais = new ByteArrayInputStream(ByteBufUtil.getBytes(buf));
                ObjectInputStream ois = new ObjectInputStream(bais);
        ){
            decoded = ois.readObject();
        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);      // TODO: Do something useful
        }
        return decoded;
    }
}



