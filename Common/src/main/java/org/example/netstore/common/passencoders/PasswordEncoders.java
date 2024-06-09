package org.example.netstore.common.passencoders;

public abstract class PasswordEncoders {
    public static PasswordEncoder getPasswordEncoder(){
        return new BcryptPasswordEncoder();
    }
}
