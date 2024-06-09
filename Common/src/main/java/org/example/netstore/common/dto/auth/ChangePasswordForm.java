package org.example.netstore.common.dto.auth;

import java.io.Serializable;

public record ChangePasswordForm(String oldPassword, String newPassword) implements Serializable {
}
