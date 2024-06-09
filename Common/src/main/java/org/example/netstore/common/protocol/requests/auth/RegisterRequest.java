package org.example.netstore.common.protocol.requests.auth;

import org.example.netstore.common.dto.auth.UserRegisterForm;
import org.example.netstore.common.protocol.requests.AuthCommand;

public class RegisterRequest extends AuthRequest{
    private final UserRegisterForm form;
    public RegisterRequest(UserRegisterForm form) {
        super(AuthCommand.REGISTER);
        this.form = form;
    }

    public UserRegisterForm getForm() {
        return form;
    }
}
