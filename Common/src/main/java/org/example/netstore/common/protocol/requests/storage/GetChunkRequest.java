package org.example.netstore.common.protocol.requests.storage;

import org.example.netstore.common.protocol.requests.StorageCommand;

import java.util.Objects;

public class GetChunkRequest extends StorageRequest {
    private final String path;
    private final long offset;
    public GetChunkRequest(String path, long offset) {
        super(StorageCommand.GET_CHUNK);
        this.path = path;
        this.offset = offset;
    }

    public String getPath() {
        return path;
    }

    public long getOffset() {
        return offset;
    }

}
