package org.example.netstore.common.protocol.responses.error;

import org.example.netstore.common.protocol.responses.ResponseSource;

import java.util.UUID;

public class StorageErrorResponse extends ErrorResponse{
    public StorageErrorResponse(String message, UUID requestUUID) {
        super(ResponseSource.STORAGE, requestUUID, message);
    }
}
