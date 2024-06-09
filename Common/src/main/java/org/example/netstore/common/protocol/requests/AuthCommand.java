package org.example.netstore.common.protocol.requests;

public enum AuthCommand {
    LOGIN,
    LOGOFF,
    REGISTER,
    GET_PROFILE,
    UPDATE_PROFILE,
    CHANGE_PASSWORD;

    @Override
    public String toString() {
        return this.name();
    }

}
