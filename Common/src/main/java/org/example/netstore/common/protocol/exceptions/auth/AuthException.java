package org.example.netstore.common.protocol.exceptions.auth;

import org.example.netstore.common.protocol.exceptions.ServiceException;

import java.util.UUID;

public class AuthException extends ServiceException {
    public AuthException(String message, UUID requestUUID) {
        super(message, requestUUID);
    }

}
