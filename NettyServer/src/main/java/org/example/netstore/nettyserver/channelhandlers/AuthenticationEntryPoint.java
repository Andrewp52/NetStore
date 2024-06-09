package org.example.netstore.nettyserver.channelhandlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.example.netstore.common.protocol.exceptions.auth.AuthException;
import org.example.netstore.common.protocol.requests.AuthCommand;
import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.requests.RequestServer;
import org.example.netstore.common.protocol.requests.RequestType;
import org.example.netstore.common.protocol.requests.auth.AuthRequest;
import org.example.netstore.common.protocol.responses.auth.LoginResponse;
import org.example.netstore.common.protocol.responses.auth.ProfileActionResponse;
import org.example.netstore.common.protocol.responses.error.AuthErrorResponse;
import org.example.netstore.common.protocol.responses.error.ServerErrorResponse;
import org.example.netstore.nettyserver.channelhandlers.requestservers.AuthRequestServerImpl;
import org.example.netstore.nettyserver.factories.AuthServices;

import java.io.IOException;

/***
 * Authentication entry point inbound handler.
 * First worker handler in the pipeline!
 * Checks if user is authenticated:
 *      if not - checks if request is "auth request",
 *             - authenticates user
 *             - adds Storage handler into pipeline
 *      if yes - passes request further or serves profile actions.
 */
public class AuthenticationEntryPoint extends SimpleChannelInboundHandler<Request> {
    public static final String AUTH = "auth";
    private final RequestServer server;

    public AuthenticationEntryPoint() {
        this.server = new AuthRequestServerImpl(AuthServices.getDaoAuthService());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        if(!isAuthenticated(ctx)){
            if(request.getRequestType().equals(RequestType.AUTH)){
                authenticate(ctx, (AuthRequest) request);
            } else {
                throw new AuthException("Auth required", request.getUUID());
            }
        } else {
            if(request.getRequestType().equals(RequestType.AUTH)){
                serveProfileActions(ctx, (AuthRequest) request);
            } else {
                ctx.fireChannelRead(request);
            }
        }
    }

    private void serveProfileActions(ChannelHandlerContext ctx, AuthRequest request){
        ProfileActionResponse response = (ProfileActionResponse) server.serve(request);
        ctx.channel().attr(AttributeKey.valueOf(AUTH)).set(response);
        ctx.pipeline().writeAndFlush(response);
    }

    private void authenticate(ChannelHandlerContext ctx, AuthRequest request) throws IOException {
        if(!request.getAuthCommand().equals(AuthCommand.LOGIN) ){
            throw new AuthException("Authentication required!", request.getUUID());
        }

        LoginResponse response = (LoginResponse) this.server.serve(request);
        ctx.channel().attr(AttributeKey.valueOf(AUTH)).set(response.getUserDto());
        try {
            ctx.pipeline().addLast(new StorageRequestHandler(response.getUserDto()));
            System.out.println("Auth completed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ctx.pipeline().writeAndFlush(response);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause instanceof AuthException exception){
            ctx.pipeline().writeAndFlush(new AuthErrorResponse(exception.getRequestUUID(), exception.getMessage()));
        } else {
            ctx.pipeline().writeAndFlush(new ServerErrorResponse(cause.getMessage()));
        }
    }

    private boolean isAuthenticated(ChannelHandlerContext ctx){
        return ctx.channel().attr(AttributeKey.valueOf(AUTH)).get() != null;
    }

}
