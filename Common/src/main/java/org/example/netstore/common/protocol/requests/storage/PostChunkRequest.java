package org.example.netstore.common.protocol.requests.storage;

import org.example.netstore.common.dto.FileChunk;
import org.example.netstore.common.protocol.requests.StorageCommand;

public class PostChunkRequest extends StorageRequest{
    private final FileChunk chunk;

    public PostChunkRequest(FileChunk chunk) {
        super(StorageCommand.POST_CHUNK);
        this.chunk = chunk;
    }

    public FileChunk getChunk() {
        return chunk;
    }
}
