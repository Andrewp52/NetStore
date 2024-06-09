package org.example.netstore.common.protocol.responses;

import java.util.UUID;

public class BooleanResponse extends Response{
    public BooleanResponse(ResponseType responseType, UUID requestUUID, ResponseSource source) {
        super(responseType, requestUUID, source);
    }


}
