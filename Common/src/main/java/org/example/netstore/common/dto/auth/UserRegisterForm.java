package org.example.netstore.common.dto.auth;

public final class UserRegisterForm extends ProfileForm {
    private final String password;

    public UserRegisterForm(String username, String password, String firstName, String lastName) {
        super(username, firstName, lastName);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
