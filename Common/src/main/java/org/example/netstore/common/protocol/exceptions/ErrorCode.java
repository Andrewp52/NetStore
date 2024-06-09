package org.example.netstore.common.protocol.exceptions;

public enum ErrorCode {
    NOT_AUTHENTICATED(0),
    LOGIN(1),
    UPDATE_PROFILE(2),
    CHANGE_PASS(3),
    DELETE_PROFILE(4),

    QUOTA_EXCEEDED(5);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
