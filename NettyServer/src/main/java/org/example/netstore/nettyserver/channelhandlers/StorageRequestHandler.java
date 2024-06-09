package org.example.netstore.nettyserver.channelhandlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.netstore.common.dto.UserDto;
import org.example.netstore.common.protocol.exceptions.storage.StorageException;
import org.example.netstore.nettyserver.channelhandlers.requestservers.RequestServer;
import org.example.netstore.common.protocol.requests.storage.StorageRequest;
import org.example.netstore.common.protocol.responses.Response;
import org.example.netstore.common.protocol.responses.error.ServerErrorResponse;
import org.example.netstore.common.protocol.responses.error.StorageErrorResponse;
import org.example.netstore.nettyserver.channelhandlers.requestservers.StorageRequestServerImpl;
import org.example.netstore.nettyserver.factories.StorageServices;

import java.io.IOException;

public class StorageRequestHandler extends SimpleChannelInboundHandler<StorageRequest> {
    private final RequestServer server;
    public StorageRequestHandler(UserDto userDto) throws IOException {
        this.server = new StorageRequestServerImpl(StorageServices.getStorageService(userDto));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StorageRequest msg) throws Exception {
            Response response = server.serve(msg);
            ctx.pipeline().writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause instanceof StorageException e){
            ctx.pipeline().writeAndFlush(new StorageErrorResponse(cause.getMessage(), e.getRequestUUID()));
        } else {
            ctx.pipeline().writeAndFlush(new ServerErrorResponse(cause.getMessage()));
        }

    }
}
