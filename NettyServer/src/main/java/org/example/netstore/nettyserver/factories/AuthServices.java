package org.example.netstore.nettyserver.factories;

import org.example.netstore.common.passencoders.PasswordEncoders;
import org.example.netstore.nettyserver.services.auth.AuthService;
import org.example.netstore.nettyserver.services.auth.DaoAuthService;

public abstract class AuthServices {
    public static AuthService getDaoAuthService(){
        return new DaoAuthService(
                DatabaseConnectors.getDatabaseConnector(),
                PasswordEncoders.getPasswordEncoder()
        );
    }
}
