package org.example.netstore.common.protocol.requests;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class Request implements Serializable {
    protected UUID uuid;
    protected final RequestType requestType;

    public Request(RequestType requestType) {
        this.requestType = requestType;
        this.uuid = UUID.randomUUID();
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public UUID regenUUID(){
        this.uuid = UUID.randomUUID();
        return this.uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(uuid, request.uuid) && requestType == request.requestType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, requestType);
    }
}
