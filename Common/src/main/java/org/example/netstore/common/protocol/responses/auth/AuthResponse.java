package org.example.netstore.common.protocol.responses.auth;

import org.example.netstore.common.protocol.responses.Response;
import org.example.netstore.common.protocol.responses.ResponseSource;
import org.example.netstore.common.protocol.responses.ResponseType;

import java.util.UUID;

public abstract class AuthResponse extends Response {
    protected AuthResponse(UUID requestUUID) {
        super(ResponseType.OK, requestUUID, ResponseSource.AUTH);
    }
}
