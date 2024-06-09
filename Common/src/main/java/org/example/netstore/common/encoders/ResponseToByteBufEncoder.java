package org.example.netstore.common.encoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.example.netstore.common.protocol.requests.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ResponseToByteBufEncoder extends MessageToByteEncoder {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        try (
                ByteArrayOutputStream bas = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bas)
        ){
            oos.writeObject(msg);
            byte[] b = bas.toByteArray();
            out.writeInt(b.length);
            out.writeBytes(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
