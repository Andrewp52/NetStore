package org.example.netstore.fxclient.netclient;

import org.example.netstore.common.dto.UserDto;
import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.responses.Response;

import java.util.function.Consumer;

public interface NetClient {
    void sendRequest(Request request, Consumer<Response> responseConsumer) throws Exception;

    void sendRequestAndWait(Request request, Consumer<Response> responseConsumer) throws Exception;

    void connect() throws Exception;

    void disconnect();

    int awaitingResponse();

    UserDto getConnectedUser();

    void close();

    ConnectionSettings getConnectionSettings();

    void setConnectionSettings(ConnectionSettings connectionSettings);

    int getChunkSize() throws Exception;
}
