package org.example.netstore.common.protocol.requests.storage;

import org.example.netstore.common.protocol.requests.StorageCommand;

public class GetChunkSizeRequest extends StorageRequest{
    public GetChunkSizeRequest() {
        super(StorageCommand.GET_CHUNK_SIZE);
    }
}
