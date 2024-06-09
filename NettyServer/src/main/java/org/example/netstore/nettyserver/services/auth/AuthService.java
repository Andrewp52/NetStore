package org.example.netstore.nettyserver.services.auth;

import org.example.netstore.common.dto.UserDto;
import org.example.netstore.nettyserver.User;

public interface AuthService {

    void deleteProfile(Integer id) throws ProfileActionException;

    User logIn(String username, String password);

    User updateProfile(UserDto user);

    boolean changePassword(String oldPass, String newPass);
}
