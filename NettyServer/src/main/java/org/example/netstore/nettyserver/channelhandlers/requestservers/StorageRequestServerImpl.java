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

public class StorageRequestServerImpl implements RequestServer {
    private final StorageService storageService;

    public StorageRequestServerImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public Response serve(Request request) {
        return switch (request) {
            case DirRequest req -> serveReq(req);
            case MkdirRequest req -> serveReq(req);
            case PostSizeCheckRequest req -> serveReq(req);
            case GetChunkSizeRequest req -> serveReq(req);
            case GetChunkRequest req -> serveReq(req);
            default -> throw new IllegalStateException("Unexpected value: " + request);
        };
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
