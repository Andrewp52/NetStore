package org.example.netstore.common.protocol.responses.auth;

import java.util.UUID;

public class LogoffResponse extends AuthResponse{
    protected LogoffResponse(UUID requestUUID) {
        super(requestUUID);
    }
}
