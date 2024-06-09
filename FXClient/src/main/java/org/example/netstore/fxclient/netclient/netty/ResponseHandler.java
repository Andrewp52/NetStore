package org.example.netstore.fxclient.netclient.netty;

import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.responses.Response;

import java.util.function.Consumer;

public interface ResponseHandler {
    void notifyResult(Response result);
    void addResultConsumer(Request request, Consumer<Response> consumer);

    void waitForConsumerDone(Request request) throws InterruptedException;

    void clearCallbacks();

    boolean isEmpty();

    int getConsumersCount();
}
