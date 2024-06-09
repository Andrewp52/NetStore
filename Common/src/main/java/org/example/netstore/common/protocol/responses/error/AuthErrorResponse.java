package org.example.netstore.common.protocol.responses.error;

import org.example.netstore.common.protocol.responses.ResponseSource;

import java.util.UUID;

public class AuthErrorResponse extends ErrorResponse{
    public AuthErrorResponse(UUID requestUUID, String message) {
        super(ResponseSource.AUTH, requestUUID, message);
    }
}
