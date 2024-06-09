package org.example.netstore.common.protocol.responses.auth;

import org.example.netstore.common.dto.UserDto;

import java.util.UUID;

public class UserRegisterResponse extends AuthResponse {
    UserDto userDto;

    protected UserRegisterResponse(UserDto dto, UUID requestUUID) {
        super(requestUUID);
        this.userDto = dto;
    }

    public UserDto getUserDto() {
        return userDto;
    }
}
