package org.example.netstore.nettyserver.channelhandlers.requestservers;

import org.example.netstore.common.dto.FileChunk;
import org.example.netstore.common.protocol.exceptions.storage.StorageException;
import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.requests.RequestServer;
import org.example.netstore.common.protocol.requests.storage.*;
import org.example.netstore.common.protocol.responses.*;
import org.example.netstore.common.protocol.responses.storage.DirResponse;
import org.example.netstore.common.protocol.responses.storage.MkdirResponse;
import org.example.netstore.nettyserver.services.store.StorageService;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class StorageRequestServerImpl implements RequestServer {
    private final StorageService storageService;

    public StorageRequestServerImpl(StorageService storageService) {
        this.storageService = storageService;
    }
    private final Map<Class<? extends Request>, Function<Request, Response>> functionMap = new HashMap<>();

    {
        Arrays.stream(StorageRequestServerImpl.class.getDeclaredMethods())
                .filter(method -> method.getReturnType().equals(Response.class))
                .forEach(method -> {
                    Class param = method.getParameterTypes()[0];
                    functionMap.put(param, request -> {
                        try {
                            return (Response) method.invoke(this, request);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
    }
    @Override
    public Response serve(Request request) {
        return functionMap.get(request.getClass()).apply(request);
    }

    private Response serveReq(GetChunkSizeRequest req) {
        return new LongResponse(storageService.getChunkSize(), req.getUUID());
    }

    private Response serveReq(MkdirRequest request) {
        return new MkdirResponse(storageService.mkDir(request), request.getUUID());
    }

    private Response serveReq(DirRequest request){
        return new DirResponse(storageService.dir(request.getPath()), request.getUUID());
    }

    private Response serveReq(PostSizeCheckRequest request){
        if(storageService.checkWriteSize(request.getStoreSize())){
            return new BooleanResponse(ResponseType.OK, request.getUUID(), ResponseSource.STORAGE);
        }
        throw new StorageException("Not enough free space", request.getUUID());
    }

    private Response serveReq(PostChunkRequest request){
        return null;
    }

    private Response serveReq(GetChunkRequest request){
        return new FileChunk(storageService.getChunk(request.getPath(), request.getOffset()), request.getUUID());
    }


}
