package org.example.netstore.common.dto.auth;

import java.io.Serializable;

public record LoginForm(String username, String password) implements Serializable {
}
