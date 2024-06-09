package org.example.netstore.common.protocol.requests.auth;

import org.example.netstore.common.dto.UserDto;
import org.example.netstore.common.protocol.requests.AuthCommand;

public class UpdateProfileRequest extends AuthRequest{
    private final UserDto dto;
    public UpdateProfileRequest(UserDto dto) {
        super(AuthCommand.UPDATE_PROFILE);
        this.dto = dto;
    }

    public UserDto getDto() {
        return dto;
    }
}
