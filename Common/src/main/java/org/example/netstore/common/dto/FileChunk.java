package org.example.netstore.common.dto;

import org.example.netstore.common.protocol.responses.storage.StorageResponse;

import java.io.Serializable;
import java.util.UUID;

public class FileChunk extends StorageResponse {
    private byte[] chunk;

    public FileChunk(byte[] chunk, UUID requestUUID) {
        super(requestUUID);
        this.chunk = chunk;
    }

    public byte[] getChunk() {
        return chunk;
    }
}
