package org.example.netstore.common.protocol.responses;


import java.io.Serializable;
import java.util.UUID;

public abstract class Response implements Serializable {
    protected UUID uuid;
    protected final ResponseType responseType;
    protected final ResponseSource source;

    protected Response(ResponseType responseType, UUID requestUUID, ResponseSource source) {
        this.responseType = responseType;
        this.source = source;
        this.uuid = requestUUID;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setUuid(UUID uuid){
        this.uuid = uuid;
    }
}
