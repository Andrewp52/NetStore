package org.example.netstore.common.protocol.requests.auth;

import org.example.netstore.common.protocol.requests.AuthCommand;

public class LoginRequest extends AuthRequest{
    private final String username;
    private final String password;
    public LoginRequest(String username, String password) {
        super(AuthCommand.LOGIN);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
