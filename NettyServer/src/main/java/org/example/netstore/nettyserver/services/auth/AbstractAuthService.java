package org.example.netstore.nettyserver.services.auth;


import org.example.netstore.common.passencoders.PasswordEncoder;

public abstract class AbstractAuthService implements AuthService{
    protected final PasswordEncoder passwordEncoder;
    public AbstractAuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



}
