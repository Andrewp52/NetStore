package org.example.netstore.common.protocol.requests.auth;

import org.example.netstore.common.protocol.requests.AuthCommand;

public class LogoffRequest extends AuthRequest{
    public LogoffRequest() {
        super(AuthCommand.LOGOFF);
    }
}
