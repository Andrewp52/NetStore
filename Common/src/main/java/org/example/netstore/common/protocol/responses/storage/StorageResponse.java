package org.example.netstore.common.protocol.responses.storage;

import org.example.netstore.common.protocol.responses.Response;
import org.example.netstore.common.protocol.responses.ResponseSource;
import org.example.netstore.common.protocol.responses.ResponseType;

import java.util.UUID;

public abstract class StorageResponse extends Response {
    protected StorageResponse(UUID requestUUID) {
        super(ResponseType.OK, requestUUID, ResponseSource.STORAGE);
    }
}
