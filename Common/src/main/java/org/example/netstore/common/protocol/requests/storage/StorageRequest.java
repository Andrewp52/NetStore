package org.example.netstore.common.protocol.requests.storage;

import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.requests.RequestType;
import org.example.netstore.common.protocol.requests.StorageCommand;

public abstract class StorageRequest extends Request {
    private final StorageCommand storageCommand;
    public StorageRequest(StorageCommand storageCommand) {
        super(RequestType.STORAGE);
        this.storageCommand = storageCommand;
    }

    public StorageCommand getStorageCommand() {
        return storageCommand;
    }
}
