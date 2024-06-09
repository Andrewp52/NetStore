package org.example.netstore.common.protocol.requests.storage;

import org.example.netstore.common.protocol.requests.StorageCommand;

public class FileInfoRequest extends StorageRequest{
    private final String path;
    public FileInfoRequest(String path) {
        super(StorageCommand.FILE_INFO);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
