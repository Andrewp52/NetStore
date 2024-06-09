package org.example.netstore.fxclient.netclient.netty;

import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.responses.Response;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ClientResponseHandler implements ResponseHandler {
    private final Map<UUID, Consumer<Response>> responseCallbacks = new ConcurrentHashMap<>();
    @Override
    public void notifyResult(Response result) {
        Consumer<Response> consumer = responseCallbacks.get(result.getUUID());
        if(consumer != null){
            consumer.accept(result);
            synchronized (consumer){
                consumer.notifyAll();
            }
            responseCallbacks.remove(result.getUUID());
        }
    }

    @Override
    public void addResultConsumer(Request request, Consumer<Response> consumer) {
        while (responseCallbacks.containsKey(request.getUUID())){
            request.regenUUID();
        }
        responseCallbacks.put(request.getUUID(), consumer);
    }

    @Override
    public void waitForConsumerDone(Request request) throws InterruptedException {
        Consumer<Response> consumer = responseCallbacks.get(request.getUUID());
        if (consumer!= null){
            synchronized (consumer){
                consumer.wait();
            }
        }
    }
    @Override
    public void clearCallbacks(){
        responseCallbacks.clear();
    }

    @Override
    public boolean isEmpty(){
        return responseCallbacks.isEmpty();
    }

    @Override
    public int getConsumersCount() {
        return responseCallbacks.size();
    }
}
