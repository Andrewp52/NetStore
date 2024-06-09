package org.example.netstore.common.protocol.responses;

import java.util.UUID;

public class LongResponse extends Response{
    private final long value;
    public LongResponse(long value, UUID requestUUID) {
        super(ResponseType.OK, requestUUID, ResponseSource.STORAGE);
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
