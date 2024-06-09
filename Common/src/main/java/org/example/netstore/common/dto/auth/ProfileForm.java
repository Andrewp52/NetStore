package org.example.netstore.common.dto.auth;


import java.io.Serializable;

public class ProfileForm implements Serializable {
    protected final String username;
    protected final String firstName;
    protected final String lastName;

    public ProfileForm(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String username() {
        return username;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

}
