package org.example.netstore.fxclient.netclient.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.netstore.common.decoders.ByteToObjectDecoder;
import org.example.netstore.common.encoders.ResponseToByteBufEncoder;
import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.responses.Response;


import java.util.ArrayList;
import java.util.List;

public class NettyClient {
    private static EventLoopGroup workerGroup;
    private Bootstrap b;
    private static final NettyClient instance = new NettyClient();;
    private final List<ResponseHandler> responseHandlers = new ArrayList<>();
    private ChannelFuture channelFuture;

    private NettyClient() {
        init();
    }

    public static NettyClient getInstance(){
        return instance;
    }


    public void connect(String host, int port) throws InterruptedException {
        if(b == null || workerGroup == null){
            init();
        }
        channelFuture = b.connect(host, port).sync();
    }

    public void sendRequest(Request request) throws Exception {
        if(isConnected()){
            channelFuture.awaitUninterruptibly().channel().writeAndFlush(request).sync();
        }
    }

    public void addResponseHandler(ResponseHandler responseHandler){
        responseHandlers.add(responseHandler);
    }

    public void removeResponseHandler(ResponseHandler responseHandler){
        responseHandlers.remove(responseHandler);
    }

    public void  disconnect() throws InterruptedException {
        if(channelFuture != null){
            channelFuture.channel().close().sync();
        }
    }

    public boolean isConnected() {
        return this.channelFuture != null && channelFuture.channel().isOpen() && channelFuture.channel().isActive();
    }

    public void close() throws InterruptedException {
        disconnect();
        if(workerGroup != null){
            workerGroup.shutdownGracefully();
            workerGroup = null;
        }
        b = null;
    }

    private void init(){
        workerGroup = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.option(ChannelOption.TCP_NODELAY, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(
                        new ByteToObjectDecoder(),
                        new SimpleChannelInboundHandler<Response>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {
                                responseHandlers.forEach(notifier -> notifier.notifyResult(msg));
                            }
                        },
                new ResponseToByteBufEncoder()
                );
            }
        });
    }


}
