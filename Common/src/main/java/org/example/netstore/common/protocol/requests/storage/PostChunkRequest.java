package org.example.netstore.common.protocol.requests.storage;

import org.example.netstore.common.dto.FileChunk;
import org.example.netstore.common.protocol.requests.StorageCommand;

public class PostChunkRequest extends StorageRequest{
    private final String path;
    private final byte[] chunk;
    private final long offset;

    public PostChunkRequest(String path, byte[] chunk, long offset) {
        super(StorageCommand.POST_CHUNK);
        this.path = path;
        this.chunk = chunk;
        this.offset = offset;
    }

    public byte[] getChunk() {
        return chunk;
    }

    public String getPath() {
        return path;
    }

    public long getOffset() {
        return offset;
    }
}
