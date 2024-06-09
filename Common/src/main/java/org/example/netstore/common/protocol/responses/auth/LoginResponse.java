package org.example.netstore.common.protocol.responses.auth;

import org.example.netstore.common.dto.UserDto;

import java.util.UUID;

public class LoginResponse extends AuthResponse{
    private final UserDto userDto;

    public LoginResponse(UserDto u, UUID requestUUID) {
        super(requestUUID);
        this.userDto = u;
    }

    public UserDto getUserDto() {
        return userDto;
    }
}
