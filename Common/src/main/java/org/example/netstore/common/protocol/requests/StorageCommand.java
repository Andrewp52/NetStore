package org.example.netstore.common.protocol.requests;

public enum StorageCommand {
    DIR,
    FILE_INFO,
    POST_SIZE_CHECK,
    POST_CHUNK,
    POST_FINISH,
    GET_CHUNK,
    COPY,
    MOVE,
    DELETE,
    MKDIR,
    GET_CHUNK_SIZE;


    @Override
    public String toString() {
        return this.name();
    }
}
