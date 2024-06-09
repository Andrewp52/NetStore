package org.example.netstore.common.protocol.responses.auth;

import java.util.UUID;

public class ChangePasswordResponse extends AuthResponse{

    public ChangePasswordResponse(UUID requestUUID) {
        super(requestUUID);
    }
}
