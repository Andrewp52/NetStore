package org.example.netstore.common.protocol.requests.storage;

import org.example.netstore.common.protocol.requests.StorageCommand;

public class GetSizeRequest extends StorageRequest{
    private final String path;
    public GetSizeRequest(String path) {
        super(StorageCommand.FILE_SIZE);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
