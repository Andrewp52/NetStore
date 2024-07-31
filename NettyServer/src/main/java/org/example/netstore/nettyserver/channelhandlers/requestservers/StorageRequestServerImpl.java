package org.example.netstore.nettyserver.channelhandlers.requestservers;

import org.example.netstore.common.dto.FileChunk;
import org.example.netstore.common.protocol.exceptions.storage.StorageException;
import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.requests.storage.*;
import org.example.netstore.common.protocol.responses.*;
import org.example.netstore.common.protocol.responses.storage.DirResponse;
import org.example.netstore.common.protocol.responses.storage.MkdirResponse;
import org.example.netstore.nettyserver.services.store.StorageService;


public class StorageRequestServerImpl extends AbstractRequestServer {
    private final StorageService storageService;

    public StorageRequestServerImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public Response serve(Request request) {
        return super.functionMap.get(request.getClass()).apply(request);
    }

    Response serveReq(GetChunkSizeRequest req) {
        return new LongResponse(storageService.getChunkSize(), req.getUUID());
    }

    Response serveReq(MkdirRequest request) {
        return new MkdirResponse(storageService.mkDir(request), request.getUUID());
    }

    Response serveReq(DirRequest request){
        return new DirResponse(storageService.dir(request.getPath()), request.getUUID());
    }

    Response serveReq(PostSizeCheckRequest request){
        if(storageService.checkWriteSize(request.getStoreSize())){
            return new BooleanResponse(true, ResponseType.OK, request.getUUID(), ResponseSource.STORAGE);
        }
        throw new StorageException("Not enough free space", request.getUUID());
    }

    Response serveReq(PostChunkRequest request){
        return new LongResponse(storageService.writeChunk(request.getPath(), request.getOffset(), request.getChunk()), request.getUUID());
    }

    Response serveReq(GetChunkRequest request){
        return new FileChunk(storageService.getChunk(request.getPath(), request.getOffset()), request.getUUID());
    }


}
