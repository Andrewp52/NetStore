package org.example.netstore.common.protocol.responses.error;

import org.example.netstore.common.protocol.responses.ResponseSource;

import java.util.UUID;

public class ServerErrorResponse extends ErrorResponse{
    public ServerErrorResponse(String message) {
        super(ResponseSource.SERVER, UUID.fromString("SERVER"), message);
    }
}
