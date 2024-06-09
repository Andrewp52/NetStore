package org.example.netstore.common.protocol.requests.auth;

import org.example.netstore.common.protocol.requests.AuthCommand;
import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.requests.RequestType;

public abstract class AuthRequest extends Request {
    private final AuthCommand authCommand;
    public AuthRequest(AuthCommand authCommand) {
        super(RequestType.AUTH);
        this.authCommand = authCommand;
    }

    public AuthCommand getAuthCommand() {
        return authCommand;
    }


}
