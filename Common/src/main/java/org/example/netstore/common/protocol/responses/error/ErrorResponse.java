package org.example.netstore.common.protocol.responses.error;

import org.example.netstore.common.protocol.responses.Response;
import org.example.netstore.common.protocol.responses.ResponseSource;
import org.example.netstore.common.protocol.responses.ResponseType;

import java.util.UUID;

public abstract class ErrorResponse extends Response {

    protected final String message;
    protected ErrorResponse(ResponseSource source, UUID requestUUID, String message) {
        super(ResponseType.ERROR, requestUUID, source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
