package org.example.netstore.fxclient.netclient.netty;

import org.example.netstore.common.dto.UserDto;
import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.requests.auth.LoginRequest;
import org.example.netstore.common.protocol.requests.storage.GetChunkSizeRequest;
import org.example.netstore.common.protocol.responses.LongResponse;
import org.example.netstore.common.protocol.responses.Response;
import org.example.netstore.common.protocol.responses.auth.LoginResponse;
import org.example.netstore.common.protocol.responses.error.ErrorResponse;
import org.example.netstore.fxclient.netclient.ConnectionSettings;
import org.example.netstore.fxclient.netclient.NetClient;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class NettyClientFasade implements NetClient {
    private ConnectionSettings connectionSettings;
    private final NettyClient nettyClient = NettyClient.getInstance();
    private final ResponseHandler responseHandler;
    private UserDto connectedUser;
    private int chunkSize;
    public NettyClientFasade(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
        this.nettyClient.addResponseHandler(responseHandler);
    }

    @Override
    public void sendRequest(Request request, Consumer<Response> responseConsumer) throws Exception {
        if(!nettyClient.isConnected() && connectedUser != null){
            throw new Exception("Client is not connected"); // TODO: Do specific exception
        }
        responseHandler.addResultConsumer(request, responseConsumer);
        nettyClient.sendRequest(request);
    }

    @Override
    public void sendRequestAndWait(Request request, Consumer<Response> responseConsumer) throws Exception {
        if(!nettyClient.isConnected() && connectedUser != null){
            throw new Exception("Client is not connected"); // TODO: Do specific exception
        }
        responseHandler.addResultConsumer(request, responseConsumer);
        nettyClient.sendRequest(request);
        responseHandler.waitForConsumerDone(request);

    }
    @Override
    public void connect() throws Exception {
        if(connectionSettings == null){
            throw new Exception("Connection settings not found!");
        }
        nettyClient.connect(this.connectionSettings.host(), this.connectionSettings.port());
        AtomicReference<String> error = new AtomicReference<>();
        sendRequestAndWait(
                new LoginRequest(this.connectionSettings.login(), this.connectionSettings.password()),
                response -> {
                    switch (response){
                        case LoginResponse lr -> this.connectedUser = lr.getUserDto();
                        case ErrorResponse er -> error.set(er.getMessage());
                        default -> throw new IllegalStateException("Unexpected value: " + response);
                    }
                });
        if(connectedUser == null){
            throw new Exception("Connection failed: %s".formatted(error.get()));
        }
    }

    @Override
    public void disconnect(){
        try {
            connectedUser = null;
            nettyClient.disconnect();
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // TODO: Do specific exception
        }
    }

    @Override
    public int awaitingResponse(){
        return responseHandler.getConsumersCount();
    }

    @Override
    public UserDto getConnectedUser(){
        return this.connectedUser;
    }

    @Override
    public void close() {
        try {
            nettyClient.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConnectionSettings getConnectionSettings() {
        return connectionSettings;
    }

    @Override
    public void setConnectionSettings(ConnectionSettings connectionSettings) {
        this.connectionSettings = connectionSettings;
    }

    @Override
    public int getChunkSize() throws Exception {
        return this.chunkSize == 0? requestChunkSize() : this.chunkSize;
    }

    private int requestChunkSize() throws Exception {
        AtomicReference<Integer> size = new AtomicReference<>();
        sendRequestAndWait(
                new GetChunkSizeRequest(),
                response -> {
                    switch (response){
                        case LongResponse res -> size.set((int) res.getValue());
                        case ErrorResponse er -> throw new RuntimeException(er.getMessage());
                        default -> throw new IllegalStateException("Unexpected value: " + response);
                    }
                });
        this.chunkSize = size.get();
        return this.chunkSize;
    }
}
