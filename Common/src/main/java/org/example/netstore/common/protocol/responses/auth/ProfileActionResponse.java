package org.example.netstore.common.protocol.responses.auth;

import org.example.netstore.common.dto.UserDto;

import java.util.UUID;

public class ProfileActionResponse extends AuthResponse{
    private final UserDto dto;

    public ProfileActionResponse(UserDto dto, UUID requestUUID) {
        super(requestUUID);
        this.dto = dto;
    }

    public UserDto getDto() {
        return dto;
    }
}
