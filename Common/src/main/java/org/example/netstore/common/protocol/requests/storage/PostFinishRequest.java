package org.example.netstore.common.protocol.requests.storage;

import org.example.netstore.common.protocol.requests.StorageCommand;

/**
 * This request type is like EOF.
 * Needs to let server know the file upload is complete.
 */
public class PostFinishRequest extends StorageRequest{
    private final String path;

    public PostFinishRequest(String path) {
        super(StorageCommand.POST_FINISH);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
