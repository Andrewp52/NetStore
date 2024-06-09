package org.example.netstore.common.protocol.requests.auth;

import org.example.netstore.common.protocol.requests.AuthCommand;

public class ChangePasswordRequest extends AuthRequest{
    private final String oldPassword;
    private final String newPassword;
    public ChangePasswordRequest(String oldPassword, String newPassword) {
        super(AuthCommand.CHANGE_PASSWORD);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
