package org.example.netstore.common.protocol.responses;

import java.util.UUID;

public class BooleanResponse extends Response{
    private final boolean value;
    public BooleanResponse(boolean value, ResponseType responseType, UUID requestUUID, ResponseSource source) {
        super(responseType, requestUUID, source);
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }
}
