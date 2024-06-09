package org.example.netstore.common.passencoders;

public interface PasswordEncoder {
    String encode(String password);
    boolean match(String hash, String password);
}
