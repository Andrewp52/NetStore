package org.example.netstore.common.protocol.requests.storage;

import org.example.netstore.common.protocol.requests.StorageCommand;

public class PostSizeCheckRequest extends StorageRequest{
    private final long storeSize;
    public PostSizeCheckRequest(long storeSize) {
        super(StorageCommand.POST_SIZE_CHECK);
        this.storeSize = storeSize;
    }
    public long getStoreSize() {
        return storeSize;
    }
}
