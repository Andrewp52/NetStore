package org.example.netstore.common.protocol.exceptions;

import java.util.UUID;

public abstract class ServiceException extends RuntimeException{
    protected UUID requestUUID;

    public ServiceException(String message, UUID requestUUID) {
        super(message);
        this.requestUUID = requestUUID;
    }

    public UUID getRequestUUID() {
        return requestUUID;
    }
}
