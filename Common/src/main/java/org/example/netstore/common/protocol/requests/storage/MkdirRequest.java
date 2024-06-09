package org.example.netstore.common.protocol.requests.storage;

import org.example.netstore.common.protocol.requests.StorageCommand;

public class MkdirRequest extends StorageRequest{
    private final String path;
    public MkdirRequest(String path) {
        super(StorageCommand.MKDIR);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
